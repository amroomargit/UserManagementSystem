package com.sword.usermanagementsystem.dtos;

import com.sword.usermanagementsystem.entities.Student;
import com.sword.usermanagementsystem.entities.Teacher;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {

    private String username;
    private String password;
    private StudentDTO studentEntity; //change name of child of user (studentEntity, not student) to avoid circular reference
    private TeacherDTO teacherEntity; //change name of child of user (studentEntity, not student) to avoid circular reference


}
