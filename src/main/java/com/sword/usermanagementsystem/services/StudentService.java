package com.sword.usermanagementsystem.services;

import com.sword.usermanagementsystem.dtos.CourseDTO;
import com.sword.usermanagementsystem.dtos.StudentDTO;
import com.sword.usermanagementsystem.mappers.CourseMapper;
import com.sword.usermanagementsystem.mappers.StudentMapper;
import com.sword.usermanagementsystem.repositories.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepo;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    CourseMapper courseMapper;

    //ManyToMany between courses and students
    @Transactional
    public List<StudentDTO> getAllStudents(){
        var students = studentRepo.findAll();

        var result = new ArrayList<StudentDTO>();
        for(var student:students){
            var courses = student.getCourses();
            var studentDTO = studentMapper.toDTO(student);
            studentDTO.setCourseList(new ArrayList<CourseDTO>());

            for(var course:courses){
                var courseDTO  = courseMapper.toDTO(course);
                studentDTO.getCourseList().add(courseDTO);

            }
            result.add(studentDTO);
        }

        return result;
    }
}
