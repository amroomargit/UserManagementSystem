package com.sword.usermanagementsystem.mappers;

import com.sword.usermanagementsystem.dtos.CourseDTO;
import com.sword.usermanagementsystem.dtos.TopicDTO;
import com.sword.usermanagementsystem.entities.Course;
import com.sword.usermanagementsystem.entities.Topic;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {TeacherMapper.class, CourseMapper.class})
public interface TopicMapper {
    //Entity to DTO
    TopicDTO toDTO(Topic topic);

    //DTO to Entity
    Topic toEntity(TopicDTO topicDTO);
}
