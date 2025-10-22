package com.sword.usermanagementsystem.mappers;

import com.sword.usermanagementsystem.dtos.CourseDTO;
import com.sword.usermanagementsystem.entities.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {TeacherMapper.class, TopicMapper.class})
public interface CourseMapper {

    //Entity to DTO
    CourseDTO toDTO(Course course);

    //DTO to Entity
    Course toEntity(CourseDTO courseDTO);
}
