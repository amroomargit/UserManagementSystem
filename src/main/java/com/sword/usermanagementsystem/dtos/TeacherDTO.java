package com.sword.usermanagementsystem.dtos;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TeacherDTO extends UserDTO{ //TeacherDTO inherits UserDTO so that we can fill out more info for studentRegistration method in UserService

    private int id;
    private String name;

    private List<CourseDTO> courseList;

    private UserDTO user;
}
