package com.sword.usermanagementsystem.dtos;

import com.sword.usermanagementsystem.entities.Student;
import com.sword.usermanagementsystem.entities.Teacher;
import com.sword.usermanagementsystem.validators.WhiteSpaceConstraint;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {

    //@NotBlank is a built-in validation constraint, it ensures the field is not null, empty, or only made of spaces
    //@WhiteSpaceConstraint is our custom validation annotation, it checks for internal whitespaces, and at beginning and end
    @NotBlank(message = "Username cannot be blank")
    @WhiteSpaceConstraint
    private String username;

    @NotBlank(message = "Password cannot be blank")
    @WhiteSpaceConstraint
    private String password;

    private StudentDTO studentEntity; //change name of child of user (studentEntity, not student) to avoid circular reference

    private TeacherDTO teacherEntity; //change name of child of user (studentEntity, not student) to avoid circular reference


}
