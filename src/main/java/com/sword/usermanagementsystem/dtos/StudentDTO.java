package com.sword.usermanagementsystem.dtos;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class StudentDTO {
    private int id;
    private String firstname;
    private String lastname;

    //Reference to CourseDTO for the ManyToMany between course and student
    private List<CourseDTO> courseList;
}
