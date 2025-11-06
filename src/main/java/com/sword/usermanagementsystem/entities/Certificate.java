package com.sword.usermanagementsystem.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "certificate")
public class Certificate {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "grade")
    private float grade;

    @Column(name = "certificatetype")
    private String certificateType;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "studentid", referencedColumnName = "id")
    private Student student;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "courseid", referencedColumnName = "id")
    private Course course;
}
