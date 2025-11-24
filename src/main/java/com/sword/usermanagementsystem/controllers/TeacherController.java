package com.sword.usermanagementsystem.controllers;

import com.sword.usermanagementsystem.dtos.TeacherDTO;
import com.sword.usermanagementsystem.entities.Teacher;
import com.sword.usermanagementsystem.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teachers")
@PreAuthorize("hasRole('ADMIN')")
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



    //Check TeacherService class for description of assignTopic method

    /* Parameters below are the integers that will be in the search bar, so
     http://localhost:8081/teachers/{teacherId}/topics/{topicId} becomes
     http://localhost:8081/teachers/1/topics/1 */

    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping("/{teacherId}/topics/{topicId}")
    public ResponseEntity<String> topicAssign(@PathVariable int teacherId, @PathVariable int topicId){
        String successMessage = service.assignTopic(teacherId,topicId);
        return ResponseEntity.ok().body(successMessage);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/insert-teacher")
    public ResponseEntity<TeacherDTO> insertTeacher(@RequestBody TeacherDTO teacherDTO){
        return ResponseEntity.ok().body(service.insertTeacher(teacherDTO));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/update-teacher-info/{teacherId}")
    public ResponseEntity<TeacherDTO> updateTeacherInfo(@PathVariable int teacherId, @RequestBody TeacherDTO teacherDTO){
        return ResponseEntity.ok().body(service.updateTeacherInfo(teacherId,teacherDTO));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete-teacher/{teacherId}")
    public ResponseEntity<String> deleteTeacher(@PathVariable int teacherId){
        return ResponseEntity.ok().body(service.deleteTeacher(teacherId));
    }

    @PatchMapping("/assign-teacher-to-a-course/{teacherId}/{courseId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> assignTeacherToACourse(@PathVariable int teacherId, @PathVariable int courseId){
        return ResponseEntity.ok().body(service.assignTeacherToACourse(teacherId,courseId));
    }

    @GetMapping("/courses-teacher/{courseId}")
    @PreAuthorize("hasAnyRole('ADMIN','STUDENT','TEACHER')")
    public ResponseEntity<TeacherDTO> coursesTeacher(@PathVariable int courseId){
        return ResponseEntity.ok().body(service.coursesTeacher(courseId));
    }
}
