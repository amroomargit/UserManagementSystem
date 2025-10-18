package com.sword.usermanagementsystem.services;

import com.sword.usermanagementsystem.dtos.EventDTO;
import com.sword.usermanagementsystem.entities.Event;
import com.sword.usermanagementsystem.mappers.EventCourseMapper;
import com.sword.usermanagementsystem.repositories.EventRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepo;

    @Autowired
    private EventCourseMapper mapper; //not sure why there's an error when there's a spring bean of the mapper class defined at the top of that class

    @Transactional
    public List<EventDTO> getAllEvents(){
        List<Event> events = eventRepo.findAll();
        List<EventDTO> eventDTOs = new ArrayList<>();

        for(Event e : events){
            //mapping each entity to a DTO
            EventDTO dto = mapper.eventToEventDTO(e);
            eventDTOs.add(dto);
        }
        return eventDTOs;
    }


    /* For One-to-Many (Teacher → Event), only needed TeacherController because each Teacher “owned” their Events. You
    can fetch all Events indirectly through /teachers/all. Now, with Many-to-Many (Event ↔ Course), neither side
    owns the other, they’re equal. You have to choose one to expose first.

    EventService and EventController, were chosen purely as a test entry point (no deeper reason). It’s arbitrary,
    you could have picked Course instead.
    */
}
