package com.sword.usermanagementsystem.services;

import com.sword.usermanagementsystem.dtos.CourseDTO;
import com.sword.usermanagementsystem.dtos.TeacherDTO;
import com.sword.usermanagementsystem.entities.Course;
import com.sword.usermanagementsystem.entities.Teacher;
import com.sword.usermanagementsystem.entities.Topic;
import com.sword.usermanagementsystem.entities.User;
import com.sword.usermanagementsystem.exceptions.BusinessException;
import com.sword.usermanagementsystem.mappers.CourseMapper;
import com.sword.usermanagementsystem.mappers.TeacherMapper;
import com.sword.usermanagementsystem.mappers.UserMapper;
import com.sword.usermanagementsystem.repositories.CourseRepository;
import com.sword.usermanagementsystem.repositories.TeacherRepository;
import com.sword.usermanagementsystem.repositories.TopicRepository;
import com.sword.usermanagementsystem.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Autowired
    UserRepository userRepo;

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserService service;

    @Autowired
    CourseRepository courseRepo;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

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

    @Transactional
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

    @Transactional
    public TeacherDTO insertTeacher(TeacherDTO teacherDTO) {
        return service.teacherRegistration(teacherDTO);
    }

    @Transactional
    public TeacherDTO updateTeacherInfo(int teacherId, TeacherDTO teacherDTO){
        Optional<Teacher> findTeacher = teacherRepo.findById(teacherId);

        if(findTeacher.isPresent()){
            findTeacher.get().setFirstName(teacherDTO.getFirstName());
            findTeacher.get().setLastName(teacherDTO.getLastName());
            return teacherMapper.toDTO(findTeacher.get());
        }

        throw new BusinessException("Unsuccessful Update.");
    }

    @Transactional
    public String deleteTeacher(int teacherId){
        Optional<Teacher> teacher = teacherRepo.findById(teacherId);

        if(teacher.isPresent()){
            teacherRepo.deleteById(teacherId);
            return "Teacher successfully deleted from teacher and users, course, and teacher_topic tables.";
        }

        throw new BusinessException("Unsuccessful Deletion.");
    }

    @Transactional
    public String assignTeacherToACourse(int teacherId, int courseId){
        Teacher teacher = teacherRepo.findById(teacherId).orElseThrow(() -> new BusinessException("Teacher "+teacherId+" not found."));
        Course course = courseRepo.findById(courseId).orElseThrow(() -> new BusinessException("Course "+courseId+" not found."));

        course.setTeacher(teacher);
        return ("Teacher "+course.getTeacher().getId()+" has been successfully assigned to course "+courseId);
    }

    @Transactional
    public TeacherDTO coursesTeacher(int courseId){
        Course course = courseRepo.findById(courseId).orElseThrow(() -> new BusinessException(String.format("no course with id %s",courseId)));

        return teacherMapper.toDTO(course.getTeacher());
    }

    @Transactional
    public List<TeacherDTO> topicsTeachers(int topicId){
        Topic topic = topicRepo.findById(topicId).orElseThrow(() -> new BusinessException(String.format("No topic with id %d",topicId)));

        return topic.getTeachers().stream().map(teacherMapper::toDTO).toList();
    }

}
