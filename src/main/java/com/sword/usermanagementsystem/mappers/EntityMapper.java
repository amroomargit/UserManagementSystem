package com.sword.usermanagementsystem.mappers;

import com.sword.usermanagementsystem.dtos.CourseDTO;
import com.sword.usermanagementsystem.dtos.TeacherDTO;
import com.sword.usermanagementsystem.entities.Course;
import com.sword.usermanagementsystem.entities.Teacher;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class EntityMapper {

    private static final DateTimeFormatter TIME_FMT = DateTimeFormatter.ISO_LOCAL_TIME; //converts LocalDateTime to String (and back if needed) to keep consist formatting

    //mapping from Course Entity to CourseDTO
    public static CourseDTO toCourseDto(Course course){
        CourseDTO dto = new CourseDTO(); //creates empty dto to populate

        //Notice how we're copying all the variables from the Course Entity into the DTO
        dto.setId(course.getId()); //.getId() method comes from the CourseRepository interface since it extends JpaRepository
        dto.setTopicid(course.getTopicid());
        dto.setStarttime(course.getStarttime());
        dto.setEndtime(course.getStarttime());

        /*instead of embedding a full TeacherDto inside the CourseDto, we set only teacherId. This breaks the entity
         cycle (Teacher → Course → Teacher) and prevents infinite JSON recursion. */
       // if(course.getTeacher() != null){
         //   dto.setTeacherid(course.getTeacher().getId());
        //}

        //finished DTO ready to be serialized to JSON by Spring.
        return dto;
    }

    //mapping from Teacher Entity to TeacherDTO
    public static TeacherDTO toTeacherDto(Teacher teacher){

        TeacherDTO dto = new TeacherDTO();

        dto.setId(teacher.getId());
        dto.setName(teacher.getName());

        if(teacher.getCourses() != null){ //might throw exception if using lazy loading

            //This uses Java Streams to transform each Course entity in the list into an CourseDto using the mapper you already saw.
            List<CourseDTO> courses = teacher.getCourses().stream().map(EntityMapper::toCourseDto).collect(Collectors.toList()); //EntityMapper::toCourseDto is a method reference — shorthand for course -> EntityMapper.toCourseDto(course)
            dto.setCourses(courses); //set the DTO list on the teacher DTO.
        }

        //return populated dto
        return dto;

        //This is safe because CourseDto only contains teacherId (not a nested TeacherDto), the returned TeacherDto
        // will contain courses but those course objects will not contain an embedded teacher object — no recursion.
    }

    //mapping from CourseDTO to Course Entity
    public static Course toCourseEntity(CourseDTO dto, Teacher teacher){

        Course e = new Course();

        e.setId(dto.getId());
        e.setTopicid(dto.getTopicid());

        e.setStarttime(dto.getStarttime());
        e.setEndtime(dto.getStarttime());

        e.setTeacher(teacher); //Setting the teacher responsible for this course. JPA requires entity relationships to be set on the owning side (Course owns the FK)

        return e;
    }

    //mapping from TeacherDTO to Teacher Entity
    public static Teacher toTeacherEntity(TeacherDTO dto){

        Teacher t = new Teacher();

        t.setId(dto.getId());
        t.setName(dto.getName());

        // courses handled separately when saving to avoid cascading surprises
        return t;
    }
}
