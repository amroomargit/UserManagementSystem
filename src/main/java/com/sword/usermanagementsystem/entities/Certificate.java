package com.sword.usermanagementsystem.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    @Column(name = "grade")
    private float grade;

    @NotBlank
    @Column(name = "certificatetype")
    private String certificateType;

    @ManyToOne //Cascading here would delete the student if we ever delete the certificate
    @JoinColumn(name = "studentid", referencedColumnName = "id")
    private Student student;

    @ManyToOne /*No cascading here because it causes an issue when deleting a student if there is a row in certificate table
    that links student, course and certificate, causing the topic to delete from its own table entirely. This only happens
    with ManyToMany relationships */
    @JoinColumn(name = "courseid", referencedColumnName = "id")
    private Course course;
    //Cascading allowed on child side, so there is in course entity class
}
