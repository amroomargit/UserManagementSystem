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
    private LocalDateTime starttime;
    private LocalDateTime endtime;

    //Stopping the reference in the list is what stops the infinite loop, not the reference at the end of the class
    private TeacherDTO teacher;

    private TopicDTO topic;
}
