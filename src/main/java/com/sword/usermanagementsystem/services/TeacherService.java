package com.sword.usermanagementsystem.services;

import com.sword.usermanagementsystem.dtos.CourseDTO;
import com.sword.usermanagementsystem.dtos.TeacherDTO;
import com.sword.usermanagementsystem.mappers.CourseMapper;
import com.sword.usermanagementsystem.mappers.TeacherMapper;
import com.sword.usermanagementsystem.repositories.TeacherRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeacherService {
    @Autowired
    TeacherRepository repo;

    @Autowired
    TeacherMapper teacherMapper;

    @Autowired
    CourseMapper courseMapper;

    @Transactional
    public List<TeacherDTO> getAllTeachers(){ //OneToMany so we only needed TeacherService, not CourseService

        var teachers = repo.findAll();

        var result = new ArrayList<TeacherDTO>();
        for(var teacher:teachers){
            var courses = teacher.getCourses();
            var teacherDTO = teacherMapper.toDTO(teacher);
            teacherDTO.setCourseList(new ArrayList<CourseDTO>());

            for(var course:courses){
                var courseDTO  = courseMapper.toDTO(course);
                teacherDTO.getCourseList().add(courseDTO);

            }
            result.add(teacherDTO);
        }

        return result;
    }
}
