package com.sword.usermanagementsystem.dtos;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class StudentDTO extends UserDTO{ //StudentDTO inherits UserDTO so we can fill out more info for studentRegistration method in UserService
    private int id;
    private String firstname;
    private String lastname;

    //Reference to CourseDTO for the ManyToMany between course and student
    private List<CourseDTO> courseList;

    private UserDTO user; //refer to parent of student by its name

    private List<CertificateDTO> certificateList;
}
