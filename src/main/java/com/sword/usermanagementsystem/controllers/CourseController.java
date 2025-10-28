package com.sword.usermanagementsystem.controllers;

import com.sword.usermanagementsystem.dtos.CourseDTO;
import com.sword.usermanagementsystem.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService service;

    @GetMapping("/all")
    public ResponseEntity<List<CourseDTO>> getAllCourses(){
        var courseReturnVariable = service.getAllCourses(); //courseReturnVariable is needed so we can see in debugger
        return ResponseEntity.ok().body(courseReturnVariable);
    }


    /* NOTE: For One-to-Many (Teacher → Course), only needed TeacherController because each Teacher “owned” their Courses. You
    can fetch all Courses indirectly through /teachers/all. Now, with Many-to-Many (Course ↔ Student), neither side
    owns the other, they’re equal. You have to choose one to expose first.

    CourseService and CourseController, were chosen purely as a test entry point (no deeper reason). It’s arbitrary,
    you could have picked Topic instead.
    */
}
