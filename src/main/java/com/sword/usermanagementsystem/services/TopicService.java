package com.sword.usermanagementsystem.services;

import com.sword.usermanagementsystem.dtos.TopicDTO;
import com.sword.usermanagementsystem.entities.Topic;
import com.sword.usermanagementsystem.repositories.TopicRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//This service is for the many to many with the student
@Service
public class TopicService {
    @Autowired
    private TopicRepository topicRepo;

    //private StudentTopicMapper mapper; //not sure why there's an error when there's a spring bean of the mapper class defined at the top of that class

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


    /* For One-to-Many (Teacher → Course), only needed TeacherController because each Teacher “owned” their Courses. You
    can fetch all Courses indirectly through /teachers/all. Now, with Many-to-Many (Course ↔ Topic), neither side
    owns the other, they’re equal. You have to choose one to expose first.

    TopicService and TopicController, were chosen purely as a test entry point (no deeper reason). It’s arbitrary,
    you could have picked Topic instead.
    */
}