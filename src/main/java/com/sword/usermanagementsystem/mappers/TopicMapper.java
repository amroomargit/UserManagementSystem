package com.sword.usermanagementsystem.mappers;

import com.sword.usermanagementsystem.dtos.CourseDTO;
import com.sword.usermanagementsystem.dtos.TopicDTO;
import com.sword.usermanagementsystem.entities.Course;
import com.sword.usermanagementsystem.entities.Teacher;
import com.sword.usermanagementsystem.entities.Topic;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {TeacherMapper.class, CourseMapper.class})
public interface TopicMapper {

    //CHECK TeacherMapper FOR EXPLANATION ON WHAT THE @MAPPING TAGS MEAN/DO

    //Entity to DTO
    @Mapping(target = "teacherIds", source = "teachers", qualifiedByName = "teachersToIds") //This line is for the ManyToMany for Teacher and Topic, if there was no ManyToMany, we wouldn't need this, but the method call below would stay the same
    TopicDTO toDTO(Topic topic);

    //DTO to Entity
    @Mapping(target = "teachers", ignore = true)
    Topic toEntity(TopicDTO topicDTO);

    //Helper method
    @Named("teachersToIds")
    default List<Integer> mapTeachersToIds(List<Teacher> teachers){
        return teachers != null ? teachers.stream().map(Teacher::getId).collect(Collectors.toList()) : null;
    }
}
