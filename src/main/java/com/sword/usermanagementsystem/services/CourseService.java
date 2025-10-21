package com.sword.usermanagementsystem.services;

import com.sword.usermanagementsystem.dtos.CourseDTO;
import com.sword.usermanagementsystem.entities.Course;
import com.sword.usermanagementsystem.mappers.CourseTopicMapper;
import com.sword.usermanagementsystem.repositories.CourseRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepo;

    @Autowired
    private CourseTopicMapper mapper; //not sure why there's an error when there's a spring bean of the mapper class defined at the top of that class

    @Transactional
    public List<CourseDTO> getAllCourses(){
        List<Course> courses = courseRepo.findAll();
        List<CourseDTO> courseDTOS = new ArrayList<>();


        for(Course e : courses){
            //mapping each entity to a DTO
            CourseDTO dto = mapper.courseToCourseDTO(e);
            courseDTOS.add(dto);
        }
        return courseDTOS;
    }


    /* For One-to-Many (Teacher → Course), only needed TeacherController because each Teacher “owned” their Courses. You
    can fetch all Courses indirectly through /teachers/all. Now, with Many-to-Many (Course ↔ Topic), neither side
    owns the other, they’re equal. You have to choose one to expose first.

    CourseService and CourseController, were chosen purely as a test entry point (no deeper reason). It’s arbitrary,
    you could have picked Topic instead.
    */
}
