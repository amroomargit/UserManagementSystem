package com.sword.usermanagementsystem.services;

import com.sword.usermanagementsystem.dtos.StudentDTO;
import com.sword.usermanagementsystem.entities.Student;
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
  //  private StudentTopicMapper mapper; //not sure why there's an error when there's a spring bean of the mapper class defined at the top of that class


    @Transactional
    public List<StudentDTO> getAllStudents(){
        List<Student> students = studentRepo.findAll();
        List<StudentDTO> studentDTOS = new ArrayList<>();


        for(Student e : students){
            //mapping each entity to a DTO
           // StudentDTO dto = mapper.studentToStudentDTO(e);
            //studentDTOS.add(dto);
        }
        return studentDTOS;
    }
}
