package com.sword.usermanagementsystem.controllers;

import com.sword.usermanagementsystem.dtos.StudentDTO;
import com.sword.usermanagementsystem.dtos.TeacherDTO;
import com.sword.usermanagementsystem.dtos.UserDTO;
import com.sword.usermanagementsystem.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;


    @GetMapping("/all") //PostMapping for receiving data
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        var userReturnVariable = service.getAllUsers();
        return ResponseEntity.ok().body(userReturnVariable);
    }


    /*@RequestBody allows us to take whatever the user enters into the local host, and to convert it into JSON format,
    and then into a UserDTO, which we are going to use in this method, to pass to the studentLogin method in UserService*/

    /*@Valid triggers the validation process of the fields username and password fields (since they were annotated
    with @NotBlank and @WhiteSpaceConstraint in UserDTO class) from the JSON that was just submitted in the JSON from
    the user */

    /* If any validation fails, Spring stops before reaching your userService.register() line and throws a
    MethodArgumentNotValidException. Your GlobalExceptionHandler or Spring’s default error handler returns an automatic
    HTTP 400 response with details. */
    @PostMapping("/register/student") //PostMapping for submitting data (like registering a new user)
    public ResponseEntity<String> registerStudent(@Valid @RequestBody StudentDTO studentDTO){ //Check doc to see why we return ResponseEntity<>
        return ResponseEntity.ok().body(service.studentRegistration(studentDTO)); //Calling method from UserService with the object we made called service
    }

    @PostMapping("/login/student")
    public ResponseEntity<String> loginStudent(@RequestBody UserDTO userDTO){ //UserDTO instead of StudentDTO because we saved the StudentDTO in the User Repo after we converted it into a User Entity
        boolean isValid = service.studentLogin(userDTO.getUsername(), userDTO.getPassword()); //Calling method from UserService with the object we made called service
        if(isValid){
            return ResponseEntity.ok().body("Login Successful.");
        }
        return ResponseEntity.ok().body("Invalid credentials");
        //Returns one of these two messages based on if the studentLogin in UserService returns true or false
    }

    @PostMapping("/register/teacher")
    public ResponseEntity<String> registerTeacher(@Valid @RequestBody TeacherDTO teacherDTO){
        return ResponseEntity.ok().body(service.teacherRegistration(teacherDTO));
    }

    @PostMapping("/login/teacher")
    public ResponseEntity<String> loginTeacher(@RequestBody UserDTO userDTO){
        boolean isValid = service.teacherLogin(userDTO.getUsername(), userDTO.getPassword());
        if(isValid){
            return ResponseEntity.ok().body("Login Successful.");
        }
        return ResponseEntity.ok().body("Invalid Credentials");
    }
}
