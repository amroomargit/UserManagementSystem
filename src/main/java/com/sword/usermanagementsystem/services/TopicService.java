package com.sword.usermanagementsystem.services;

import com.sword.usermanagementsystem.dtos.CourseDTO;
import com.sword.usermanagementsystem.dtos.StudentDTO;
import com.sword.usermanagementsystem.dtos.TeacherDTO;
import com.sword.usermanagementsystem.dtos.TopicDTO;
import com.sword.usermanagementsystem.entities.Course;
import com.sword.usermanagementsystem.entities.Student;
import com.sword.usermanagementsystem.entities.Teacher;
import com.sword.usermanagementsystem.entities.Topic;
import com.sword.usermanagementsystem.exceptions.BusinessException;
import com.sword.usermanagementsystem.mappers.CourseMapper;
import com.sword.usermanagementsystem.mappers.TopicMapper;
import com.sword.usermanagementsystem.repositories.CourseRepository;
import com.sword.usermanagementsystem.repositories.StudentRepository;
import com.sword.usermanagementsystem.repositories.TeacherRepository;
import com.sword.usermanagementsystem.repositories.TopicRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//This service is for the many to many with the student
@Service
public class TopicService {
    @Autowired
    TopicRepository topicRepo;

    @Autowired
    CourseRepository courseRepo;

    @Autowired
    TeacherRepository teacherRepo;

    @Autowired
    CourseMapper courseMapper;

    @Autowired
    TopicMapper topicMapper;

    @Autowired
    StudentRepository studentRepo;

    @Transactional
    public List<TopicDTO> getTopics(){
        var topics = topicRepo.findAll();

        var result = new ArrayList<TopicDTO>();
        for(var topic:topics){
            var courses = topic.getCourses();
            var topicDTO = topicMapper.toDTO(topic);
            topicDTO.setCourseList(new ArrayList<CourseDTO>());

            for(var course:courses){
                var courseDTO  = courseMapper.toDTO(course);
                topicDTO.getCourseList().add(courseDTO);

            }
            result.add(topicDTO);
        }

        return result;
    }


    @Transactional
    public TopicDTO newTopic(TopicDTO topicDTO){
        if(topicRepo.findByName(topicDTO.getName()).isPresent()){
            throw new BusinessException("Topic Already Exists.");
        }
        topicRepo.save(topicMapper.toEntity(topicDTO));
        return topicDTO;
    }

    @Transactional
    public TopicDTO updateTopicInfo(int topicId, TopicDTO topicDTO){
        Optional<Topic> findTopic = topicRepo.findById(topicId);

        if(findTopic.isPresent()){
            Topic topic = findTopic.get();

            topic.setName(topicDTO.getName());

            List<Course> updatedCourses = topicDTO.getCourseList().stream().map(courseDTO -> courseRepo.findById(courseDTO.getId()).orElseThrow(()-> new BusinessException("Course not found with ID: " + courseDTO.getId()))).toList();
            topic.setCourses(updatedCourses);

            List<Teacher> updatedTeachers = topicDTO.getTeacherIds().stream().map(teacherId -> teacherRepo.findById(teacherId).orElseThrow(()-> new BusinessException("Teacher not found with ID: " + teacherId))).toList();
            topic.setTeachers(updatedTeachers);

            return topicDTO;
        }

        throw new BusinessException("Unsuccessful Update");
    }

    @Transactional
    public String deleteTopic(int topicId){
        Optional<Topic> findTopic = topicRepo.findById(topicId);
        if(findTopic.isPresent()){
            topicRepo.deleteById(topicId);
            return "Topic: "+ topicId +" has been successfully deleted from topic, course and teacher_topic tables.";
        }
        throw new BusinessException("Unsuccessful Deletion.");

    }

    @Transactional
    public List<TopicDTO> getAStudentsTopics(int studentId){
        Student student = studentRepo.findById(studentId).orElseThrow(()->new BusinessException(String.format("There is no student with Id %d",studentId)));

        /* This gets a list of TopicDTOs of all the topics a student takes, but since there is an indirect link between
         student and topic (we have to access topic via course), we do it this way */
        return student.getCourses().stream().map(Course::getTopic).map(topicMapper::toDTO).toList();
    }

    @Transactional
    public List<TopicDTO> teachersTopics(int teacherId){
        Teacher teacher = teacherRepo.findById(teacherId).orElseThrow(() -> new BusinessException(String.format("No teacher with id %d",teacherId)));

        return teacher.getTopics().stream().map(topicMapper::toDTO).toList();
    }






    /* For One-to-Many (Teacher → Course), only needed TeacherController because each Teacher “owned” their Courses. You
    can fetch all Courses indirectly through /teachers/all. Now, with Many-to-Many (Course ↔ Topic), neither side
    owns the other, they’re equal. You have to choose one to expose first.

    TopicService and TopicController, were chosen purely as a test entry point (no deeper reason). It’s arbitrary,
    you could have picked Topic instead.
    */
}