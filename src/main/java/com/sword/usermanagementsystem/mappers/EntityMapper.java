package com.sword.usermanagementsystem.mappers;

import com.sword.usermanagementsystem.dtos.EventDTO;
import com.sword.usermanagementsystem.dtos.TeacherDTO;
import com.sword.usermanagementsystem.entities.Event;
import com.sword.usermanagementsystem.entities.Teacher;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class EntityMapper {

    private static final DateTimeFormatter TIME_FMT = DateTimeFormatter.ISO_LOCAL_TIME; //converts LocalDateTime to String (and back if needed) to keep consist formatting

    //mapping from Event Entity to EventDTO
    public static EventDTO toEventDto(Event event){
        EventDTO dto = new EventDTO(); //creates empty dto to populate

        //Notice how we're copying all the variables from the Event Entity into the DTO
        dto.setId(event.getId()); //.getId() method comes from the EventRepository interface since it extends JpaRepository
        dto.setCourseid(event.getCourseid());
        dto.setStarttime(event.getStarttime());
        dto.setEndtime(event.getStarttime());

        /*instead of embedding a full TeacherDto inside the EventDto, we set only teacherId. This breaks the entity
         cycle (Teacher → Event → Teacher) and prevents infinite JSON recursion. */
       // if(event.getTeacher() != null){
         //   dto.setTeacherid(event.getTeacher().getId());
        //}

        //finished DTO ready to be serialized to JSON by Spring.
        return dto;
    }

    //mapping from Teacher Entity to TeacherDTO
    public static TeacherDTO toTeacherDto(Teacher teacher){

        TeacherDTO dto = new TeacherDTO();

        dto.setId(teacher.getId());
        dto.setName(teacher.getName());

        if(teacher.getEvents() != null){ //might throw exception if using lazy loading

            //This uses Java Streams to transform each Event entity in the list into an EventDto using the mapper you already saw.
            List<EventDTO> events = teacher.getEvents().stream().map(EntityMapper::toEventDto).collect(Collectors.toList()); //EntityMapper::toEventDto is a method reference — shorthand for event -> EntityMapper.toEventDto(event)
            dto.setEvents(events); //set the DTO list on the teacher DTO.
        }

        //return populated dto
        return dto;

        //This is safe because EventDto only contains teacherId (not a nested TeacherDto), the returned TeacherDto
        // will contain events but those event objects will not contain an embedded teacher object — no recursion.
    }

    //mapping from EventDTO to Event Entity
    public static Event toEventEntity(EventDTO dto, Teacher teacher){

        Event e = new Event();

        e.setId(dto.getId());
        e.setCourseid(dto.getCourseid());

        e.setStarttime(dto.getStarttime());
        e.setEndtime(dto.getStarttime());

        e.setTeacher(teacher); //Setting the teacher responsible for this event. JPA requires entity relationships to be set on the owning side (Event owns the FK)

        return e;
    }

    //mapping from TeacherDTO to Teacher Entity
    public static Teacher toTeacherEntity(TeacherDTO dto){

        Teacher t = new Teacher();

        t.setId(dto.getId());
        t.setName(dto.getName());

        // events handled separately when saving to avoid cascading surprises
        return t;
    }
}
