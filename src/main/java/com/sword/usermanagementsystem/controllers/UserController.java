package com.sword.usermanagementsystem.controllers;

import com.sword.usermanagementsystem.dtos.StudentDTO;
import com.sword.usermanagementsystem.dtos.TeacherDTO;
import com.sword.usermanagementsystem.dtos.UserDTO;
import com.sword.usermanagementsystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;


    @GetMapping("/all") //PostMapping for receiving data)
    public List<UserDTO> getAllUsers(){
        var userReturnVariable = service.getAllUsers();
        return userReturnVariable;
    }


    @PostMapping("/register/student") //PostMapping for submitting data (like registering a new user)
    public String registerStudent(@RequestBody StudentDTO studentDTO){
        return service.studentRegistration(studentDTO); //Calling method from UserService with the object we made called service
    }


    /*@RequestBody allows us to take whatever the user enters into the local host, and to convert it into JSON format,
    and then into a UserDTO, which we are going to use in this method, to pass to the studentLogin method in UserService*/
    @PostMapping("/login/student")
    public String loginStudent(@RequestBody UserDTO userDTO){ //UserDTO instead of StudentDTO because we saved the StudentDTO in the User Repo after we converted it into a User Entity
        boolean isValid = service.studentLogin(userDTO.getUsername(), userDTO.getPassword()); //Calling method from UserService with the object we made called service
        return isValid ? "Login successful" : "Invalid credentials"; //Returns one of these two messages based on if the studentLogin in UserService returns true or false
    }


    @PostMapping("/register/teacher")
    public String registerTeacher(@RequestBody TeacherDTO teacherDTO){
        return service.teacherRegistration(teacherDTO);
    }

    @PostMapping("/login/teacher")
    public String loginTeacher(@RequestBody UserDTO userDTO){
        boolean isValid = service.teacherLogin(userDTO.getUsername(), userDTO.getPassword());
        return isValid ? "Login Successful." : "Invalid Credentials";
    }
}
