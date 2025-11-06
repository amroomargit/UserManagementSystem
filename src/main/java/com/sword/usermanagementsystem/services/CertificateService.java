package com.sword.usermanagementsystem.services;

import com.sword.usermanagementsystem.dtos.CertificateDTO;
import com.sword.usermanagementsystem.mappers.CertificateMapper;
import com.sword.usermanagementsystem.mappers.StudentMapper;
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

    @Transactional
    public List<CertificateDTO> getAllCertificates(){
        return certificateRepo.findAll().stream().map(certificate -> {
            CertificateDTO dto = certificateMapper.toDTO(certificate);
            dto.setCourseEntity(certificateMapper.toDTO(certificate.getCourse()));
            dto.setStudentEntity(certificateMapper.toDTO(certificate.getStudent()));
            return dto;
        }).collect(Collectors.toList());
        }
    }
}
