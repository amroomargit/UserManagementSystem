package com.sword.usermanagementsystem.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
public class StudentDtos {
    private int age;
    private String name;
}
