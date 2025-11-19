package com.sword.usermanagementsystem.services;

import com.sword.usermanagementsystem.dtos.CertificateDTO;
import com.sword.usermanagementsystem.entities.Certificate;
import com.sword.usermanagementsystem.exceptions.BusinessException;
import com.sword.usermanagementsystem.mappers.CertificateMapper;
import com.sword.usermanagementsystem.mappers.StudentMapper;
import com.sword.usermanagementsystem.mappers.CourseMapper;
import com.sword.usermanagementsystem.repositories.CertificateRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CertificateService {

    @Autowired
    CertificateRepository certificateRepo;

    @Autowired
    CertificateMapper certificateMapper;

    @Autowired
    CourseMapper courseMapper;

    @Autowired
    StudentMapper studentMapper;

    @Transactional
    public List<CertificateDTO> getAllCertificates(){
        var certificates = certificateRepo.findAll();
        var result = new ArrayList<CertificateDTO>();

        for(var certificate:certificates){
            var certificateDTO = certificateMapper.toDTO(certificate);

            /*In the getAll methods in the courseService for example, we only did a nested loop for the students and
            * not teacher or topics even though they also have a relationship with the course, because teacher and topic
            * handled the relationship in their own services, but this isn't the case with certificate */

            //Map associated course
            if(certificate.getCourse() != null){
                var courseDTO = courseMapper.toDTO(certificate.getCourse());
                certificateDTO.setCourse(courseDTO);
            }

            //Map associated student
            if(certificate.getStudent() != null){
                var studentDTO = studentMapper.toDTO(certificate.getStudent());
                certificateDTO.setStudent(studentDTO);
            }

            result.add(certificateDTO);
        }

        return result;
    }

    @Transactional
    public CertificateDTO insertCertificate(CertificateDTO certificateDTO){
        certificateRepo.save(certificateMapper.toEntity(certificateDTO));
        return certificateDTO;
    }

    @Transactional
    public CertificateDTO updateCertificateInfo(CertificateDTO certificateDTO, int certificateId){
        Optional<Certificate> findCertificate = certificateRepo.findById(certificateId);

        if(findCertificate.isPresent()){
            findCertificate.get().setCertificateType(certificateDTO.getCertificateType());
            findCertificate.get().setGrade(certificateDTO.getGrade());
            findCertificate.get().setStudent(studentMapper.toEntity(certificateDTO.getStudent()));
            findCertificate.get().setCourse(courseMapper.toEntity(certificateDTO.getCourse()));
            return certificateDTO;
        }
        throw new BusinessException("Certificate with id: "+certificateId+" does not exist.");
    }

    @Transactional
    public String deleteCertificate(int certificateId){
        Optional<Certificate> findCertificate = certificateRepo.findById(certificateId);

        if(findCertificate.isPresent()){
            certificateRepo.deleteById(certificateId);
            return ("Certificate with Id: " +certificateId+ " has been successfully deleted.");
        }

        throw new BusinessException("Certificate with id: "+certificateId+" does not exist.");
    }
}
