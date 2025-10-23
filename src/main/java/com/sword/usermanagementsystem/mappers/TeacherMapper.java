package com.sword.usermanagementsystem.mappers;

import com.sword.usermanagementsystem.dtos.TeacherDTO;
import com.sword.usermanagementsystem.dtos.TopicDTO;
import com.sword.usermanagementsystem.entities.Teacher;
import com.sword.usermanagementsystem.entities.Topic;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TeacherMapper {

    //Entities to DTOs
    List<TeacherDTO> toDTOs(List<Teacher> teacher);

    //DTO to Entity
    List<Teacher> toEntities(List<TeacherDTO> teacherDTO);




    //Entity to DTO
    //@Mapping(target = "courses", ignore = true) //to avoid infinite loop we ignore = true
    TeacherDTO toDTO(Teacher teacher);

    //DTO to Entity
    Teacher toEntity(TeacherDTO teacherDTO);



}
