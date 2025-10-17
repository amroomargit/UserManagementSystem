package com.sword.usermanagementsystem.dtos;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TeacherDTO {

    private int id;
    private String name;

    private List<EventDTO> events;
}
