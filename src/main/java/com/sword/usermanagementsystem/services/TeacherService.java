package com.sword.usermanagementsystem.services;

import com.sword.usermanagementsystem.dtos.CourseDTO;
import com.sword.usermanagementsystem.dtos.TeacherDTO;
import com.sword.usermanagementsystem.entities.Teacher;
import com.sword.usermanagementsystem.entities.Topic;
import com.sword.usermanagementsystem.mappers.CourseMapper;
import com.sword.usermanagementsystem.mappers.TeacherMapper;
import com.sword.usermanagementsystem.repositories.TeacherRepository;
import com.sword.usermanagementsystem.repositories.TopicRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeacherService {
    @Autowired
    TeacherRepository teacherRepo;

    @Autowired
    TopicRepository topicRepo;

    @Autowired
    TeacherMapper teacherMapper;

    @Autowired
    CourseMapper courseMapper;

    @Transactional
    public List<TeacherDTO> getAllTeachers(){ //OneToMany so we only needed TeacherService, not CourseService

        var teachers = teacherRepo.findAll();

        var result = new ArrayList<TeacherDTO>();
        for(var teacher:teachers){
            var courses = teacher.getCourses();
            var teacherDTO = teacherMapper.toDTO(teacher);
            teacherDTO.setCourseList(new ArrayList<CourseDTO>());

            for(var course:courses){
                var courseDTO  = courseMapper.toDTO(course);
                teacherDTO.getCourseList().add(courseDTO);

            }
            result.add(teacherDTO);
        }

        return result;
    }

    //This method assigns an existing Topic to the Topic list in the Teacher Entity we specify
    public String assignTopic(int teacherId, int topicId){

        /*Creating an empty teacher entity to pull the corresponding entity saved in the Teacher Repo and assign it to
         this empty object we just created. If the id is not found in the teacher repo, throw an exception because the
         Entity we are trying to pull doesn't exist. We are pulling an Entity, not creating a new one here. */
        Teacher teacher = teacherRepo.findById(teacherId).orElseThrow();

        //Same with topic. We are pulling an Entity, not creating a new one here.
        Topic topic = topicRepo.findById(topicId).orElseThrow();

        /* We want to get the list of Topic Entity objects associated with that specific Teacher Entity, and we want to
         add the topic we just pulled, and add it into that list. So basically, the Teacher with id 1 will
         also teach Topic with id 3 (the Topic we pulled). */
        teacher.getTopics().add(topic);

        //Save the Teacher Entity whose Topic list we just extended, back into the Teacher Repo
        teacherRepo.save(teacher);

        //Return a success message, and if you want to view the changes we just made, check the teacher_topic join table
        return "Topic Assigned To Teacher Successfully.";
    }
}
