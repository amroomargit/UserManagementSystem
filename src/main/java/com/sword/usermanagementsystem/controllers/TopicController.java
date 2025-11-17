package com.sword.usermanagementsystem.controllers;

import com.sword.usermanagementsystem.dtos.TopicDTO;
import com.sword.usermanagementsystem.services.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/topics")
public class TopicController {

    @Autowired
    private TopicService service;

    @GetMapping("/all")
    public ResponseEntity<List<TopicDTO>> getTopics(){ //Check doc to see why we return ResponseEntity<>
        var topicReturnVariable = service.getAllTopics(); //topicReturnVariable is needed so we can see in debugger
        return ResponseEntity.ok().body(topicReturnVariable);
    }

    @PostMapping("/insert-topic")
    @PreAuthorize(("hasRole('ADMIN')"))
    public ResponseEntity<TopicDTO> newTopic(@RequestBody TopicDTO topicDTO){
        return ResponseEntity.ok().body(service.newTopic(topicDTO));
    }

    @PostMapping("/update-topic-info/{topicId}")
    @PatchMapping(("hasRole('ADMIN')"))
    public ResponseEntity<TopicDTO> updateTopicInfo(@PathVariable int topicId, @RequestBody TopicDTO topicDTO){
        return ResponseEntity.ok().body(service.updateTopicInfo(topicId, topicDTO));
    }

    @DeleteMapping("/delete-topic/{topicId}")
    @PreAuthorize(("hasRole('ADMIN')"))
    public ResponseEntity<String> deleteTopic(@PathVariable int topicId){
        return ResponseEntity.ok().body(service.deleteTopic(topicId));
    }


}