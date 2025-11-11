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
    private String name;
    private LocalDateTime starttime;
    private LocalDateTime endtime;

    //Reference to TeacherDTO for the ManyToOne between course and teacher
    private TeacherDTO teacher; //Stopping the reference in the list is what stops the infinite loop, not the reference at the end of the class

    //Reference to TopicDTO for the ManyToOne between course and topic
    private TopicDTO topic;

    //Reference to StudentDTO for the ManyToMany between course and student
    private List<StudentDTO> studentList;

    private List<CertificateDTO> certificateList;

}
