package com.sword.usermanagementsystem.controllers;

import com.sword.usermanagementsystem.dtos.StudentDTO;
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
    private StudentService service;

    @GetMapping("/all")
    public List<StudentDTO> getAllStudents(){
        var studentReturnVariable = service.getAllStudents(); //The variable x is needed so we can see in debugger
        return studentReturnVariable;
    }



    /* NOTE: For One-to-Many (Teacher → Course), only needed TeacherController because each Teacher “owned” their Courses. You
    can fetch all Courses indirectly through /teachers/all. Now, with Many-to-Many (Student ↔ Student), neither side
    owns the other, they’re equal. You have to choose one to expose first.

    StudentService and StudentController, were chosen purely as a test entry point (no deeper reason). It’s arbitrary,
    you could have picked Topic instead.
    */
}
