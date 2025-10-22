package com.sword.usermanagementsystem.services;

import com.sword.usermanagementsystem.dtos.CourseDTO;
import com.sword.usermanagementsystem.dtos.StudentDTO;
import com.sword.usermanagementsystem.mappers.CourseMapper;
import com.sword.usermanagementsystem.mappers.StudentMapper;
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

    @Autowired
    CourseMapper courseMapper;

    @Autowired
    StudentMapper studentMapper;

    //ManyToMany between courses and students
    @Transactional
    public List<CourseDTO> getAllCourses(){

        var courses = repo.findAll();

        var result = new ArrayList<CourseDTO>();
        for(var course:courses){
            var students = course.getStudents();
            var courseDTO = courseMapper.toDTO(course);
            courseDTO.setStudentList(new ArrayList<StudentDTO>());

            for(var student:students){
                var studentDTO  = studentMapper.toDTO(student);
                courseDTO.getStudentList().add(studentDTO);

            }
            result.add(courseDTO);
        }

        return result;
    }
}
