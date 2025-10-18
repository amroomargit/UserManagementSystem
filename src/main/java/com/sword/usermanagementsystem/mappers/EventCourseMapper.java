package com.sword.usermanagementsystem.mappers;

import com.sword.usermanagementsystem.dtos.CourseDTO;
import com.sword.usermanagementsystem.dtos.EventDTO;
import com.sword.usermanagementsystem.entities.Course;
import com.sword.usermanagementsystem.entities.Event;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;


//This mapper is for a ManyToMany relationship between the Event and Course entities

@Mapper(componentModel = "spring") //Generates spring bean so this class can be Autowired
public interface EventCourseMapper {


    /* ************* Event Entity to DTO ************* */

    /* The eventToEventDTO method below maps an entity to DTO and converts the list of Course objects into a list of
    Integer IDs using helper @Named method */

    /* In the @Mapping tag, the source is getting mapped to the target, so in this case, "courses" (which is a list of
    Course objects) imported from the Event entity class, is getting mapped to the "courseIds" (which is a list of
    Integer objects) from EventDTO. Remember, there's only two columns in the newly joined table in a ManyToMany
    relationship between two entities, which is why we are only storing Integers in courseIds, they're the primary keys
    of the course entity. Notice that even though this is for mapping events, we're dealing in courses. Go check the
    comment at the bottom of the eventDTO class, and you'll see why we're dealing in courses and not events here.*/
    @Mapping(source = "courses", target = "courseIds", qualifiedByName = "coursesToIds")
    EventDTO eventToEventDTO(Event event);



    /* ************* Event DTO to Entity ************* */

    /* Maps back from DTO to entity, ignoring the List<Course> courses imported from the Event entity class so that we
    can manually attach course entities in the service layer instead. If we didn't ignore this here, then an infinite
    loop would happen again */

    //Read the "No need for Course DTO to Entity" section below for more info
    @Mapping(target = "courses", ignore = true)
    Event eventDTOtoEvent(EventDTO dto);



    /* ************* Helper method that maps List<Course> -> List<Integer> ************* */
    @Named("coursesToIds")
    default List<Integer> coursesToIds(List<Course> courses){
        if(courses == null){
            return null;
        }
        return courses.stream().map(Course::getId).collect(Collectors.toList());
    }






    /* ************* Course Entity to DTO ************* */

    /* The eventToEventDTO method below maps an entity to DTO and converts the list of Course objects into a list of
    Integer IDs using helper @Named method */

    /* In the @Mapping tag, the source is getting mapped to the target, so in this case, "events" (which is a list of
    Event objects) imported from the Event entity class, is getting mapped to the "eventIds" (which is a list of
    Integer objects) from CourseDTO. Remember, there's only two columns in the newly joined table in a ManyToMany
    relationship between two entities, which is why we are only storing Integers in eventIds, they're the primary keys
    of the event entity. Notice that even though this is for mapping events, we're dealing in events. Go check the
    comment at the bottom of the courseDTO class, and you'll see why we're dealing in events and not courses here.*/
    @Mapping(source = "events", target = "eventIds", qualifiedByName = "eventsToIds")
    CourseDTO courseToCourseDTO(Course course);



    /* ************* No need for Course DTO to Entity ************* */
    /* In this particular case there is no need because we don't run the risk of an infinite loop. This is because
     MapStruct interface matches variables with the same name in the entity and DTO class. Basically, MapStruct will
     ignore eventIds because there’s no eventIds field in Course entity like there is in the DTO. However, since there
     is a courseId in both the Event entity and DTO, it would have mapped them, causing that recurring reference,
     causing that infinite loop, which is why we have to explicitly ignore it.*/



    /* ************* Helper method that maps List<Event> -> List<Event> ************* */
    @Named("eventsToIds")
    default List<Integer> eventsToIds(List<Event> events){
        if(events == null){
            return null;
        }
        return events.stream().map(Event::getId).collect(Collectors.toList());
    }
}
