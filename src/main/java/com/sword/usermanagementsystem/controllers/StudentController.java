package com.sword.usermanagementsystem.controllers;

import com.sword.usermanagementsystem.entities.Student;
import com.sword.usermanagementsystem.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    StudentService service;

    @GetMapping
    public List<Student> getStudents(){
        return service.getAllStudents();
    }
}
