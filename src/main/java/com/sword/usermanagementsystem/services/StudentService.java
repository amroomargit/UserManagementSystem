package com.sword.usermanagementsystem.services;

import com.sword.usermanagementsystem.dtos.CourseDTO;
import com.sword.usermanagementsystem.dtos.StudentDTO;
import com.sword.usermanagementsystem.entities.Student;
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

    /* Method that returns a StudentDTO by the id. The student repository has a built-in findById method, so we use that to find
    the student matching the id, then the student we found gets mapped into a DTO using the toDTO method we made in StudentMapper class */
    public StudentDTO getStudentById(int id){
        Student student = studentRepo.findById(id).orElseThrow(() -> new RuntimeException("Could not find a student with id: "+id));
        /* var optional = studentRepo.findById(id);
        optional.ifPresent((a)->{});
        if(optional.isPresent()){
            var student1 = optional.get();
        } */
        return studentMapper.toDTO(student);
    }


}
