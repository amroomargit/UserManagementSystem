package com.sword.usermanagementsystem.dtos;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class StudentDTO {
    private int age;
    private String name;

    //Reference to CourseDTO for the ManyToMany between course and student
    private List<CourseDTO> courseList;
}
