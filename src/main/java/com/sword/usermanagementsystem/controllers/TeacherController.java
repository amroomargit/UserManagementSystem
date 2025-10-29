package com.sword.usermanagementsystem.controllers;

import com.sword.usermanagementsystem.dtos.TeacherDTO;
import com.sword.usermanagementsystem.entities.Teacher;
import com.sword.usermanagementsystem.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/teachers")
public class TeacherController {

    @Autowired
    private TeacherService service;

    @GetMapping("/all")
    /* Only needed teacher controller and not course controller because it was a
    OneToMany relationship, so we were retrieving list from the one teacher object */
    public ResponseEntity<List<TeacherDTO>> getTeachers(){ //Check doc to see why we return ResponseEntity<>
        var teacherReturnVariable = service.getAllTeachers();
        return ResponseEntity.ok().body(teacherReturnVariable);
        //teacherReturnVariable is needed so we can see in debugger
    }
}
