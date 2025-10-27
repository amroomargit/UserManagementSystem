package com.sword.usermanagementsystem.dtos;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TeacherDTO {

    private int id;
    private String name;

    private List<CourseDTO> courseList;

    private UserDTO user;
}
