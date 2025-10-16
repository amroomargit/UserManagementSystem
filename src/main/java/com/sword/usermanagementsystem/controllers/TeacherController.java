package com.sword.usermanagementsystem.controllers;

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
    public List<Teacher> getTeachers(){
        var x = service.getAllTeachers();
        return null;
    }
}
