package com.sword.usermanagementsystem.controllers;

import com.sword.usermanagementsystem.dtos.TopicDTO;
import com.sword.usermanagementsystem.services.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<TopicDTO> getTopics(){
        var topicReturnVariable = service.getAllTopics(); //topicReturnVariable is needed so we can see in debugger
        return topicReturnVariable;
    }
}