package com.sword.usermanagementsystem.services;

import com.sword.usermanagementsystem.dtos.CertificateDTO;
import com.sword.usermanagementsystem.mappers.CertificateMapper;
import com.sword.usermanagementsystem.mappers.StudentMapper;
import com.sword.usermanagementsystem.mappers.CourseMapper;
import com.sword.usermanagementsystem.repositories.CertificateRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
}
