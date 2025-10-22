package com.sword.usermanagementsystem.mappers;

import com.sword.usermanagementsystem.dtos.StudentDTO;
import com.sword.usermanagementsystem.entities.Student;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    //Entity to DTO
    StudentDTO toDTO(Student student);

    //DTO to Entity
    Student toEntity(StudentDTO studentDTO);
}
