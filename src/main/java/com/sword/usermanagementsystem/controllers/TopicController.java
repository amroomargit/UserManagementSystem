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

    //this controller is for the topic and student many-to-many relationship

    @Autowired
    private TopicService service;

    @GetMapping("/all")
    public List<TopicDTO> getTopics(){ //only needed teacher controller and not course controller because it was a
        // OneToMany relationship, so we were retrieving list from the one teacher object
        var x = service.getAllTopics();
        return x;
    }
}