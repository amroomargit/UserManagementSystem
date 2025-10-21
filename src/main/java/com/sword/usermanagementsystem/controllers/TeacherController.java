package com.sword.usermanagementsystem.controllers;

import com.sword.usermanagementsystem.dtos.TeacherDTO;
import com.sword.usermanagementsystem.entities.Teacher;
import com.sword.usermanagementsystem.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/teachers")
public class TeacherController {

    @Autowired
    TeacherService service;

    @GetMapping("/all")
    public List<TeacherDTO> getTeachers(){ //only needed teacher controller and not course controller because it was a
        // OneToMany relationship, so we were retrieving list from the one teacher object
        var x = service.getAllTeachers();
        return x;
    }
}
