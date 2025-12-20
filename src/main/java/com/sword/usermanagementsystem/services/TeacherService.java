package com.sword.usermanagementsystem.services;

import com.sword.usermanagementsystem.dtos.CourseDTO;
import com.sword.usermanagementsystem.dtos.TeacherDTO;
import com.sword.usermanagementsystem.dtos.TopicDTO;
import com.sword.usermanagementsystem.entities.Course;
import com.sword.usermanagementsystem.entities.Teacher;
import com.sword.usermanagementsystem.entities.Topic;
import com.sword.usermanagementsystem.entities.User;
import com.sword.usermanagementsystem.exceptions.BusinessException;
import com.sword.usermanagementsystem.mappers.CourseMapper;
import com.sword.usermanagementsystem.mappers.TeacherMapper;
import com.sword.usermanagementsystem.mappers.TopicMapper;
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
    TopicMapper topicMapper;

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
    public String assignTopicToTeacher(int teacherId, int topicId){

        /*Creating an empty teacher entity to pull the corresponding entity saved in the Teacher Repo and assign it to
         this empty object we just created. If the id is not found in the teacher repo, throw an exception because the
         Entity we are trying to pull doesn't exist. We are pulling an Entity, not creating a new one here. */
        Teacher teacher = teacherRepo.findById(teacherId).orElseThrow();

        //Same with topic. We are pulling an Entity, not creating a new one here.
        Topic topic = topicRepo.findById(topicId).orElseThrow();

        //Make sure the topic isn't already assigned to the teacher
        if(teacher.getTopics().contains(topic)){
            throw new BusinessException(topic.getName() +" is already assigned to "+teacher.getFirstName() + " " + teacher.getLastName());
        }

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
        Teacher teacher = teacherRepo.findById(teacherId).orElseThrow(() -> new BusinessException("Teacher not found"));

        for(Course course : teacher.getCourses()){
            course.setTeacher(null);
        }

        teacherRepo.delete(teacher);

        return "Teacher deleted and courses preserved.";
    }

    @Transactional
    public String assignTeacherToACourse(int teacherId, int courseId){
        Teacher teacher = teacherRepo.findById(teacherId).orElseThrow(() -> new BusinessException("Teacher "+teacherId+" not found."));
        Course course = courseRepo.findById(courseId).orElseThrow(() -> new BusinessException("Course "+courseId+" not found."));

        String teacherName = teacher.getFirstName() + " " + teacher.getLastName();

         /*If the teacher is not assigned to the topic of the course (if the teacher doesn't have the same topic as the course
        assigned to them in the teacher_topic table in the database*/
        if(!teacher.getTopics().contains(course.getTopic())){
            throw new BusinessException(teacherName + " is not permitted to teach this course because the course's " +
                    "topic has not been assigned to the teacher. If you wish to have " + teacherName + " teach this course, " +
                    "then assign the topic " + course.getTopic() + " to them first.");
        }

        Teacher courseCurrentTeacher = course.getTeacher();

        //Check if null
        if(courseCurrentTeacher!=null){
            int courseCurrentTeacherId = courseCurrentTeacher.getId();
            String courseCurrentTeacherName = courseCurrentTeacher.getFirstName() + " " + courseCurrentTeacher.getLastName();

            //If same teacher already teaches this course
            if(teacher.getId() == courseCurrentTeacherId){
                throw new BusinessException(teacherName+" already teaches this course.");
            }

            //Set teacher
            course.setTeacher(teacher);

            //If someone else taught the course before and has been replaced
            if(teacher.getId() != courseCurrentTeacherId){
                return (teacherName+ " has replaced "+courseCurrentTeacherName+" as the teacher of this course.");
            }
        }

        course.setTeacher(teacher);

        //If no one taught the course
        return (teacherName+ " has been successfully assigned to course "+course.getName());
    }

    @Transactional
    public List<CourseDTO> getCoursesOfATeacher(int teacherId){
        Teacher teacher = teacherRepo.findById(teacherId).orElseThrow(() -> new BusinessException(String.format("There is no teacher with id: ",teacherId)));

        if(teacher.getCourses().isEmpty()){
            throw new BusinessException(String.format("%s does not teach any courses.",(teacher.getFirstName()+" "+teacher.getLastName())));
        }

        return teacher.getCourses().stream().map(courseMapper::toDTO).toList();
    }

    @Transactional
    public List<TopicDTO> getTopicsOfATeacher(int teacherId){
        Teacher teacher = teacherRepo.findById(teacherId).orElseThrow(() -> new BusinessException(String.format("There is no teacher with id: ",teacherId)));

        if(teacher.getTopics().isEmpty()){
            throw new BusinessException(String.format("%s does not teach any topics.",(teacher.getFirstName()+" "+teacher.getLastName())));
        }

        return teacher.getTopics().stream().map(topicMapper::toDTO).toList();
    }

}
