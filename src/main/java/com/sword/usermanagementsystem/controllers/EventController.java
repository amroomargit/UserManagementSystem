package com.sword.usermanagementsystem.controllers;

import com.sword.usermanagementsystem.dtos.EventDTO;
import com.sword.usermanagementsystem.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventService service;

    @GetMapping("/all")
    public List<EventDTO> getAllEvents(){
        // Call the service to fetch events and map them
        return service.getAllEvents();
    }
}
