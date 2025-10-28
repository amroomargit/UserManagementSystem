package com.sword.usermanagementsystem.controllers;

import com.sword.usermanagementsystem.dtos.StudentDTO;
import com.sword.usermanagementsystem.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService service;

    @GetMapping("/all")
    public ResponseEntity<List<StudentDTO>> getAllStudents(){
        var studentReturnVariable = service.getAllStudents(); //studentReturnVariable is needed so we can see in debugger
        return ResponseEntity.ok().body(studentReturnVariable);
    }


    /* The id we put in http://localhost:8080/students/student/1 (in this example it's 1) gets passed as a parameter,
    * and we call the getStudentById method we created in the StudentService class to return the DTO associated with
    * that id, by finding the student entity and converting it into a DTO, and sending it here, where we return the
    * DTO we got back. */
    @GetMapping("student/{id}")
    public ResponseEntity<StudentDTO> getStudent(@PathVariable int id){
        StudentDTO studentDTO = service.getStudentById(id);
        return ResponseEntity.ok().body(studentDTO);
    }


    /* NOTE: For One-to-Many (Teacher → Course), only needed TeacherController because each Teacher “owned” their Courses. You
    can fetch all Courses indirectly through /teachers/all. Now, with Many-to-Many (Student ↔ Student), neither side
    owns the other, they’re equal. You have to choose one to expose first.

    StudentService and StudentController, were chosen purely as a test entry point (no deeper reason). It’s arbitrary,
    you could have picked Topic instead.
    */
}
