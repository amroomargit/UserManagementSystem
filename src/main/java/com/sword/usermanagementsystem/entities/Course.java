package com.sword.usermanagementsystem.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "topic")
    private String topic;

    @Column(name = "introdate")
    private LocalDateTime introdate;

    @ManyToMany(mappedBy = "courses") //"courses" because that's the list name in Event class
    private List<Event> events; //List for Many to Many, not just regular object like Many To One
}
