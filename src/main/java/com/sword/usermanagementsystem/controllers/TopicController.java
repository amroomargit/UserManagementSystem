package com.sword.usermanagementsystem.controllers;

import com.sword.usermanagementsystem.dtos.CourseDTO;
import com.sword.usermanagementsystem.dtos.TeacherDTO;
import com.sword.usermanagementsystem.dtos.TopicDTO;
import com.sword.usermanagementsystem.entities.Course;
import com.sword.usermanagementsystem.services.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/topics")
public class TopicController {

    @Autowired
    private TopicService service;

    @GetMapping("/all")
    public ResponseEntity<List<TopicDTO>> getTopics(){ //Check doc to see why we return ResponseEntity<>
        return ResponseEntity.ok().body(service.getTopics());
    }

    @PostMapping("/insert-topic")
    @PreAuthorize(("hasRole('ADMIN')"))
    public ResponseEntity<TopicDTO> newTopic(@RequestBody TopicDTO topicDTO){
        return ResponseEntity.ok().body(service.newTopic(topicDTO));
    }

    @PutMapping("/update-topic-info/{topicId}")
    @PreAuthorize(("hasRole('ADMIN')"))
    public ResponseEntity<TopicDTO> updateTopicInfo(@PathVariable int topicId, @RequestBody TopicDTO topicDTO){
        return ResponseEntity.ok().body(service.updateTopicInfo(topicId, topicDTO));
    }

    @DeleteMapping("/delete-topic/{topicId}")
    @PreAuthorize(("hasRole('ADMIN')"))
    public ResponseEntity<Map<String,String>> deleteTopic(@PathVariable int topicId){
        String msg = service.deleteTopic(topicId);
        return ResponseEntity.ok(Map.of("Message",msg));
    }

    @GetMapping("/students-topics/{studentId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<TopicDTO>> getAStudentsTopics (@PathVariable int studentId){
        return ResponseEntity.ok().body(service.getAStudentsTopics(studentId));
    }

    @GetMapping("/topics-teachers/{topicId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<TeacherDTO>> topicsTeachers(@PathVariable int topicId){
        return ResponseEntity.ok().body(service.topicsTeachers(topicId));
    }

}