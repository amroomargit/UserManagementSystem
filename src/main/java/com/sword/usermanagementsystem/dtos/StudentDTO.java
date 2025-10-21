package com.sword.usermanagementsystem.dtos;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class StudentDTO {
    private int age;
    private String name;

    // Has to be list of Integer objects referencing each other's primary keys because a list of DTO objects
    // for a ManyToMany relationship causes infinite recursion
    private List<Integer> topicIds;
}
