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
    /*With this new edit, everything works the same, except now we decide which fields can and cannot have whitespaces
    by toggling true or false under canContainSpaces. In any DTO in the future we add these annotations to, we can do
    the same.*/
    @NotBlank(message = "Username cannot be blank")
    @WhiteSpaceConstraint(canContainSpaces = false)
    private String username;

    @NotBlank(message = "Password cannot be blank")
    @WhiteSpaceConstraint(canContainSpaces = false)
    private String password;

    private StudentDTO studentEntity; //change name of child of user (studentEntity, not student) to avoid circular reference

    private TeacherDTO teacherEntity; //change name of child of user (studentEntity, not student) to avoid circular reference

    private AdminDTO adminEntity;

    //These two methods literally exist just so that we can see the first and last name in the frontend
    public String getFirstName(){
        if(studentEntity!=null){
            return studentEntity.getFirstName();
        }
        return null;
    }

    public String getLastName(){
        if(studentEntity!=null){
            return studentEntity.getLastName();
        }
        return null;
    }

}
