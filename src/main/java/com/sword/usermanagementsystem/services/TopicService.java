package com.sword.usermanagementsystem.services;

import com.sword.usermanagementsystem.dtos.TopicDTO;
import com.sword.usermanagementsystem.entities.Course;
import com.sword.usermanagementsystem.entities.Teacher;
import com.sword.usermanagementsystem.entities.Topic;
import com.sword.usermanagementsystem.exceptions.BusinessException;
import com.sword.usermanagementsystem.mappers.CourseMapper;
import com.sword.usermanagementsystem.mappers.TopicMapper;
import com.sword.usermanagementsystem.repositories.CourseRepository;
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

    @Transactional
    public List<TopicDTO> getAllTopics(){
        List<Topic> topics = topicRepo.findAll();
        List<TopicDTO> topicDTOS = new ArrayList<>();


        for(Topic e : topics){
            //mapping each entity to a DTO
           // TopicDTO dto = mapper.topicToTopicDTO(e);
           // topicDTOS.add(dto);
        }
        return topicDTOS;
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


    /* For One-to-Many (Teacher → Course), only needed TeacherController because each Teacher “owned” their Courses. You
    can fetch all Courses indirectly through /teachers/all. Now, with Many-to-Many (Course ↔ Topic), neither side
    owns the other, they’re equal. You have to choose one to expose first.

    TopicService and TopicController, were chosen purely as a test entry point (no deeper reason). It’s arbitrary,
    you could have picked Topic instead.
    */
}