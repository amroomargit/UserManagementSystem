package com.sword.usermanagementsystem.services;

import com.sword.usermanagementsystem.dtos.StudentDTO;
import com.sword.usermanagementsystem.dtos.TeacherDTO;
import com.sword.usermanagementsystem.dtos.UserDTO;
import com.sword.usermanagementsystem.entities.Student;
import com.sword.usermanagementsystem.entities.Teacher;
import com.sword.usermanagementsystem.entities.User;
import com.sword.usermanagementsystem.mappers.StudentMapper;
import com.sword.usermanagementsystem.mappers.TeacherMapper;
import com.sword.usermanagementsystem.mappers.UserMapper;
import com.sword.usermanagementsystem.repositories.StudentRepository;
import com.sword.usermanagementsystem.repositories.TeacherRepository;
import com.sword.usermanagementsystem.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    StudentRepository studentRepo;

    @Autowired
    TeacherMapper teacherMapper;

    @Autowired
    TeacherRepository teacherRepo;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Transactional
    public List<UserDTO> getAllUsers(){
        return userRepo.findAll().stream().map(user -> {
            UserDTO dto = userMapper.toDTO(user);
            dto.setStudentEntity(studentMapper.toDTO(user.getStudent()));
            dto.setTeacherEntity(teacherMapper.toDTO(user.getTeacher()));
            return dto;
        }).collect(Collectors.toList());
    }


    @Transactional
    public String studentRegistration(StudentDTO studentDTO){

        //check if username is already taken
        if(userRepo.findByUsername(studentDTO.getUsername()).isPresent()){
            return "Username Already Taken";
            /*No loop prompting for another attempt because Spring Boot REST API is stateless, no ongoing input/output
            loop. Backend should not be waiting for another input, instead, the frontend will try a new prompt.*/
        }

        //Encode password (one-way hash & salt using the BCrypt algorithm) (check Google Doc for more info)
        String encodedPassword = passwordEncoder.encode(studentDTO.getPassword());
        studentDTO.setPassword(encodedPassword);

        //Save to database
        User userEntity = userMapper.toEntity(studentDTO); //StudentDTO class inherits UserDTO class, so a StudentDTO object counts as a UserDTO, and can be saved as a User Entity, so we can enter it into the User Repo later
        userRepo.save(userEntity); //Converted from DTO to Entity, so we can save in the repo

        Student studentEntity = studentMapper.toEntity(studentDTO); //Converting StudentDTO, so we can save Student Entity into Student Repo as well
        studentEntity.setUser(userEntity); //Able to setUser because user is an instance variable in the Student class from when we made the OneToOne relationship with User class
        studentRepo.save(studentEntity); //Save into student repo as well

        return "Student Registered Successfully.";
    }


    public boolean studentLogin(String username, String rawPassword){

        //Check if entered username is present in database
        Optional<User> userOpt = userRepo.findByUsername(username);
        if(userOpt.isEmpty()){
            return false;
        }


        //Compare raw password with stored hash

        /*We pulled the UserDTO (which is what we stored at the end of the studentRegistration method) from the
        database when we called userOpt above to check if the username is present, so here, we are getting the password from
        that UserDTO where we found the username stored in*/
        String storedHash = userOpt.get().getPassword();
        return passwordEncoder.matches(rawPassword,storedHash);
    }


    @Transactional
    public String teacherRegistration(TeacherDTO teacherDTO){
        if(userRepo.findByUsername(teacherDTO.getUsername()).isPresent()){
            return "Username Already Taken.";
        }

        String encodedPassword = passwordEncoder.encode(teacherDTO.getPassword());
        teacherDTO.setPassword(encodedPassword);

        User userEntity = userMapper.toEntity(teacherDTO);
        userRepo.save(userEntity);

        Teacher teacherEntity = teacherMapper.toEntity(teacherDTO);
        teacherEntity.setUser(userEntity);
        teacherRepo.save(teacherEntity);

        return "Teacher Registered Successfully";
    }

    public boolean teacherLogin(String username, String rawPassword){
        Optional<User> userOpt = userRepo.findByUsername(username);
        if(userOpt.isEmpty()){
            return false;
        }

        String storedHash = userOpt.get().getPassword();
        return passwordEncoder.matches(rawPassword,storedHash);
    }

}
