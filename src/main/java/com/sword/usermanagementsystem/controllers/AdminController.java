package com.sword.usermanagementsystem.controllers;

import com.sword.usermanagementsystem.dtos.AdminDTO;
import com.sword.usermanagementsystem.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admins")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    AdminService service;

    @GetMapping(("/all"))
    public List<AdminDTO> getAllAdmins(){
        var adminReturnVariable = service.getAllAdmins();
        return adminReturnVariable;
    }
}
