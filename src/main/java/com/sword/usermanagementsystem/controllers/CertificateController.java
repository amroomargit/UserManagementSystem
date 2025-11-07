package com.sword.usermanagementsystem.controllers;

import com.sword.usermanagementsystem.dtos.CertificateDTO;
import com.sword.usermanagementsystem.services.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/certificates")
@PreAuthorize("hasRole('ADMIN')")
public class CertificateController {

    @Autowired
    private CertificateService service;

    @GetMapping("/all")
    public List<CertificateDTO> getAllCertificates(){
        var certificateReturnVariable = service.getAllCertificates();
        return certificateReturnVariable;
    }
}
