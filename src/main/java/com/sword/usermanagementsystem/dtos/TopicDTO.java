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
    private String course;
    private LocalDateTime introdate;

    /* Has to be list of Integer objects referencing each other's primary keys because a list of DTO objects
    for a ManyToMany relationship causes infinite recursion */
    private List<Integer> courseIds;
}
