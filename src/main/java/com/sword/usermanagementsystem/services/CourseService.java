package com.sword.usermanagementsystem.services;

import com.sword.usermanagementsystem.dtos.CourseDTO;
import com.sword.usermanagementsystem.repositories.CourseRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseService {

    @Autowired
    CourseRepository repo;

    @Transactional
    public List<CourseDTO> getAllCourses(){

        var courses = repo.findAll();
        List<CourseDTO> courseDTOS = new ArrayList<>();

        for(var course:courses){
            //var dto = EntityMapper2.toCourseDto(course);
            //courseDTOS.add(dto);
        }
        return courseDTOS;
    }
}
