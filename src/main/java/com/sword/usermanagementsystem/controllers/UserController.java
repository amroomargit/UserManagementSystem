package com.sword.usermanagementsystem.controllers;

import com.sword.usermanagementsystem.dtos.*;
import com.sword.usermanagementsystem.entities.User;
import com.sword.usermanagementsystem.repositories.UserRepository;
import com.sword.usermanagementsystem.services.JwtService;
import com.sword.usermanagementsystem.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private JwtService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepo;

    @PreAuthorize("hasRole('ADMIN')")
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

    /*We have to have a separate register for student and teacher because we want them to appear in the Users table
    * after registering a new user, but also in either the student or teacher table depending on who registered. The login
    * can be just one thing because we are pulling from the Users table when logging in, so it doesn't really matter,
    * but if we made the register centralized, we would have to include some other sort of way in the code to figure out
    * if it was a student or teacher, which is extra work when this works just fine*/
    @PostMapping("/register/teacher")
    public ResponseEntity<TeacherDTO> registerTeacher(@Valid @RequestBody TeacherDTO teacherDTO){
        return ResponseEntity.ok().body(service.teacherRegistration(teacherDTO));
    }

    @PostMapping("/register/admin")
    public ResponseEntity<String> registerAdmin(@Valid @RequestBody AdminDTO adminDTO){
        return ResponseEntity.ok().body(service.adminRegistration(adminDTO));
    }

    @PostMapping("/login")
    //Return type is generic ? because now several responses could be returned, an error, a pass, a string, etc.
    public ResponseEntity<?> login(@RequestBody UserDTO userDTO){ //UserDTO instead of StudentDTO or TeacherDTO because we saved them in the User Repo after we converted them into User Entities in their respective classes
        UserDTO user = service.login(userDTO.getUsername(), userDTO.getPassword()); //Returning true or false to confirm that the user we are trying to log in as A. exists, and B. username and password were entered correctly
        if(user != null){

            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDTO.getUsername(),userDTO.getPassword()));

            if(authentication.isAuthenticated()){
                User userEntity = userRepo.findByUsername(userDTO.getUsername()).orElse(null);

                if(userEntity == null){
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found.");
                }

                String token = jwtService.generateToken(userEntity);
                Map<String,String> response = new HashMap<>();
                response.put("token",token);
                response.put("role", userEntity.getRole());
                return ResponseEntity.ok(response);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials"); //HTTP 401 Unauthorized response if authentication fails
    }

    @PatchMapping("/change-password/{userId}")
    @PreAuthorize("isAuthenticated() or hasRole('ADMIN')")
    public ResponseEntity<String> changePassword(@PathVariable int userId, @RequestBody ChangePasswordDTO changePasswordDTO){
        return ResponseEntity.ok().body(service.changePassword(userId, changePasswordDTO));
    }
}
