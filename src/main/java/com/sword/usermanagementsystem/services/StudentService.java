package com.sword.usermanagementsystem.services;

import com.sword.usermanagementsystem.entities.Student;
import com.sword.usermanagementsystem.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    @Autowired
    StudentRepository repo;

    public List<Student> getAllStudents(){
        return repo.findAll();
    }
}
