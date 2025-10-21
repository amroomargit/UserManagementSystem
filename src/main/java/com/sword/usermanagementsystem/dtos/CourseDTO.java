package com.sword.usermanagementsystem.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CourseDTO {

    private int id;
    private int topicid;
    private LocalDateTime starttime;
    private LocalDateTime endtime;
    //private Integer teacherid; //not needed, null because we already know it's owner

    //Stopping the reference in the list is what stops the infinite loop, not the reference at the end of the class
    private TeacherDTO teacher;

    // Has to be list of Integer objects referencing each other's primary keys because a list of DTO objects
    // for a ManyToMany relationship causes infinite recursion
    private List<Integer> topicIds;
}
