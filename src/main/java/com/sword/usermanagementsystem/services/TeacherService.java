package com.sword.usermanagementsystem.services;

import com.sword.usermanagementsystem.entities.Teacher;
import com.sword.usermanagementsystem.repositories.TeacherRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {
    @Autowired
    TeacherRepository repo;

    @Transactional
    public List<Teacher> getAllTeachers(){
        return repo.findAll();
    }
}
