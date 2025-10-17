package com.sword.usermanagementsystem.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class EventDTO {

    private int id;
    private int courseid;
    private LocalDateTime starttime;
    private LocalDateTime endtime;
    private Integer teacherid; //For reference only, not a teacher object

    //Removing the call to TeacherDTO stops the recursive call, ending the loop.
}
