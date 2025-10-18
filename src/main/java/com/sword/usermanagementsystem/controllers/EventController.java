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

    /* For One-to-Many (Teacher → Event), only needed TeacherController because each Teacher “owned” their Events. You
    can fetch all Events indirectly through /teachers/all. Now, with Many-to-Many (Event ↔ Course), neither side
    owns the other, they’re equal. You have to choose one to expose first.

    EventService and EventController, were chosen purely as a test entry point (no deeper reason). It’s arbitrary,
    you could have picked Course instead.
    */
}
