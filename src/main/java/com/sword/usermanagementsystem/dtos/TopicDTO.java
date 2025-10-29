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

    private List<Integer> teacherIds;
}
