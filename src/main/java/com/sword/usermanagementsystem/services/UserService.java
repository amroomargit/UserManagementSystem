package com.sword.usermanagementsystem.services;

import com.sword.usermanagementsystem.dtos.StudentDTO;
import com.sword.usermanagementsystem.dtos.TeacherDTO;
import com.sword.usermanagementsystem.dtos.UserDTO;
import com.sword.usermanagementsystem.mappers.StudentMapper;
import com.sword.usermanagementsystem.mappers.TeacherMapper;
import com.sword.usermanagementsystem.mappers.UserMapper;
import com.sword.usermanagementsystem.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserRepository userRepo;

    @Autowired
    UserMapper userMapper;

    @Autowired
    StudentMapper studentMapper;

    @Autowired
    TeacherMapper teacherMapper;

    @Transactional
    public List<UserDTO> getAllUsers(){
        return userRepo.findAll().stream().map(user -> {
            UserDTO dto = new UserDTO();
            dto.setUsername(user.getUsername());
            dto.setPassword(user.getPassword());
            dto.setStudentDTO(studentMapper.toDTO(user.getStudent()));
            dto.setTeacherDTO(teacherMapper.toDTO(user.getTeacher()));
            return dto;
        }).collect(Collectors.toList());
    }

}
