package com.sword.usermanagementsystem.controllers;

import com.sword.usermanagementsystem.dtos.TopicDTO;
import com.sword.usermanagementsystem.services.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}