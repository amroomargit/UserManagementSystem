package com.sword.usermanagementsystem.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TopicDTO {

    private int id;
    private String name;
    private String course;
    private LocalDateTime introdate;

    //For the Topic and Course One-To-Many relationship
    private List<CourseDTO> courseList;

    /* Has to be a list of Integer objects referencing each other's primary keys because a list of DTO objects
    for a ManyToMany relationship causes infinite recursion */
    //private List<Integer> studentIds;

    //private List<TeacherDTO> teacherList;
}
