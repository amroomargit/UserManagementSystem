package com.sword.usermanagementsystem.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CertificateDTO {
    private int id;
    private float grade;
    private String certificateType;

    private StudentDTO student;
    private CourseDTO course;
}
