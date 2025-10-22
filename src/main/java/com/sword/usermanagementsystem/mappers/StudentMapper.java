package com.sword.usermanagementsystem.mappers;

import com.sword.usermanagementsystem.dtos.StudentDTO;
import com.sword.usermanagementsystem.dtos.TeacherDTO;
import com.sword.usermanagementsystem.entities.Student;
import com.sword.usermanagementsystem.entities.Teacher;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    //Entity to DTO
    StudentDTO toDTO(Student student);

    //DTO to Entity
    Student toEntity(StudentDTO studentDTO);



    //Entities to DTO
    List<StudentDTO> toDTOs(List<Student> students);

    //DTOs to Entity
    List<Student> toEntities(List<StudentDTO> studentDTOs);
}
