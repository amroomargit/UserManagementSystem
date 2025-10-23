package com.sword.usermanagementsystem.controllers;

import com.sword.usermanagementsystem.dtos.UserDTO;
import com.sword.usermanagementsystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping("/all")
    public List<UserDTO> getAllUsers(){
        var userReturnVariable = service.getAllUsers();
        return userReturnVariable;
    }
}
