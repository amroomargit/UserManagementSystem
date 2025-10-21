package com.sword.usermanagementsystem.services;

import com.sword.usermanagementsystem.dtos.TeacherDTO;
import com.sword.usermanagementsystem.entities.Teacher;
import com.sword.usermanagementsystem.mappers.EntityMapper;
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

    @Transactional
    public List<TeacherDTO> getAllTeachers(){ //OneToMany so we only needed TeacherService, not CourseService

        var teachers = repo.findAll();
        List<TeacherDTO> teacherDTOS = new ArrayList<>();

        for(var teacher:teachers){
            var dto = EntityMapper.toTeacherDto(teacher);
            teacherDTOS.add(dto);
        }
        return teacherDTOS;
    }
}
