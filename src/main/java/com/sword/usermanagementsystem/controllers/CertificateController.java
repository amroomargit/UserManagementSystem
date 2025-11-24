package com.sword.usermanagementsystem.controllers;

import com.sword.usermanagementsystem.dtos.CertificateDTO;
import com.sword.usermanagementsystem.services.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/certificates") // SHOULD BE /api/certificates for all controllers
@PreAuthorize("hasRole('ADMIN')")
public class CertificateController {

    @Autowired
    private CertificateService service;

    @GetMapping("/all")
    public List<CertificateDTO> getAllCertificates(){
        var certificateReturnVariable = service.getAllCertificates();
        return certificateReturnVariable;
    }

    @PostMapping("/insert-certificate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CertificateDTO> insertCertificate(@RequestBody CertificateDTO certificateDTO){
        return ResponseEntity.ok().body(service.insertCertificate(certificateDTO));
    }

    @PatchMapping("/update-certificate-info/{certificateId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CertificateDTO> updateCertificateInfo(@RequestBody CertificateDTO certificateDTO, @PathVariable int certificateId){
        return ResponseEntity.ok().body(service.updateCertificateInfo(certificateDTO, certificateId));
    }

    @DeleteMapping("/delete-certificate/{certificateId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteCertificate(@PathVariable int certificateId){
        return ResponseEntity.ok().body(service.deleteCertificate(certificateId));
    }

    @GetMapping("/print-certificate/{certificateId}")
    @PreAuthorize("hasAnyRole('ADMIN','STUDENT')")
    public ResponseEntity<CertificateDTO> printCertificate(@PathVariable int certificateId){
        return ResponseEntity.ok().body(service.printCertificate(certificateId));
    }

    @GetMapping("/get-Certificate-By-Student-Id/{studentId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CertificateDTO>> getCertificateByStudentId(@PathVariable int studentId){
        return ResponseEntity.ok().body(service.getCertificateByStudentId(studentId));
    }

    @GetMapping("/print-all-certificates-from-course/{courseId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CertificateDTO>> printCertificatesByCourse(@PathVariable int courseId){
        return ResponseEntity.ok().body(service.printCertificatesByCourse(courseId));
    }

}
