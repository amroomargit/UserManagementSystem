package com.sword.usermanagementsystem.services;

import com.sword.usermanagementsystem.dtos.AdminDTO;
import com.sword.usermanagementsystem.mappers.AdminMapper;
import com.sword.usermanagementsystem.mappers.StudentMapper;
import com.sword.usermanagementsystem.mappers.TeacherMapper;
import com.sword.usermanagementsystem.repositories.AdminRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {

    @Autowired
    AdminRepository adminRepo;

    @Autowired
    AdminMapper adminMapper;

    @Autowired
    StudentMapper studentMapper;

    @Autowired
    TeacherMapper teacherMapper;

    @Transactional
    public List<AdminDTO> getAllAdmins(){
        var admins = adminRepo.findAll();
        var result = new ArrayList<AdminDTO>();

        for(var admin:admins){
            var adminDTO = adminMapper.toDTO(admin);

            result.add(adminDTO);
        }
        return result;
    }
}
