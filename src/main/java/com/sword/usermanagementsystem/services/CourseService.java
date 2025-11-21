package com.sword.usermanagementsystem.services;

import com.sword.usermanagementsystem.dtos.CourseDTO;
import com.sword.usermanagementsystem.dtos.StudentDTO;
import com.sword.usermanagementsystem.entities.*;
import com.sword.usermanagementsystem.exceptions.BusinessException;
import com.sword.usermanagementsystem.mappers.CourseMapper;
import com.sword.usermanagementsystem.mappers.StudentMapper;
import com.sword.usermanagementsystem.mappers.TeacherMapper;
import com.sword.usermanagementsystem.mappers.TopicMapper;
import com.sword.usermanagementsystem.repositories.CertificateRepository;
import com.sword.usermanagementsystem.repositories.CourseRepository;
import com.sword.usermanagementsystem.repositories.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    CourseRepository courseRepo;

    @Autowired
    CourseMapper courseMapper;

    @Autowired
    StudentMapper studentMapper;

    @Autowired
    StudentRepository studentRepo;

    @Autowired
    CertificateRepository certificateRepo;

    @Autowired
    TeacherMapper teacherMapper;

    @Autowired
    TopicMapper topicMapper;

    //ManyToMany between courses and students
    @Transactional
    public List<CourseDTO> getAllCourses(){

        var courses = courseRepo.findAll();

        var result = new ArrayList<CourseDTO>();
        for(var course:courses){
            var students = course.getStudents();
            var courseDTO = courseMapper.toDTO(course);
            courseDTO.setStudentList(new ArrayList<StudentDTO>());

            for(var student:students){
                var studentDTO  = studentMapper.toDTO(student);
                courseDTO.getStudentList().add(studentDTO);

            }
            result.add(courseDTO);
        }

        return result;
    }

    @Transactional
    public CourseDTO insertCourse(CourseDTO courseDTO){
        if(courseRepo.findByName(courseDTO.getName()).isPresent()){
            throw new BusinessException("Course Already Exists.");
        }
        courseRepo.save(courseMapper.toEntity(courseDTO));
        return courseDTO;
    }

    @Transactional
    public CourseDTO updateCourseInfo(int courseId, CourseDTO courseDTO){
        Optional<Course> findCourse = courseRepo.findById(courseId);

        if(findCourse.isPresent()){
            Course course = findCourse.get();

            course.setName(courseDTO.getName());

            List<Student> updatedStudents = courseDTO.getStudentList().stream().map(studentDTO -> studentRepo.findById(studentDTO.getId()).orElseThrow(()-> new BusinessException("Student not found with ID: " + studentDTO.getId()))).toList();
            course.setStudents(updatedStudents);

            List<Certificate> updatedCertificates = courseDTO.getCertificateList().stream().map(certificateDTO -> certificateRepo.findById(certificateDTO.getId()).orElseThrow(()-> new BusinessException("Certificate not found with ID: " + certificateDTO.getId()))).toList();
            course.setCertificates(updatedCertificates);

            course.setName(courseDTO.getName());
            course.setStarttime(courseDTO.getStarttime());
            course.setEndtime(courseDTO.getEndtime());
            course.setTeacher(teacherMapper.toEntity(courseDTO.getTeacher()));
            course.setTopic(topicMapper.toEntity(courseDTO.getTopic()));

            return courseDTO;
        }

        throw new BusinessException("Unsuccessful Update");
    }

    @Transactional
    public String deleteCourse(int courseId){
        Optional<Course> findCourse = courseRepo.findById(courseId);
        if(findCourse.isPresent()){
            courseRepo.deleteById(courseId);
            return "Course: "+ courseId +" has been successfully deleted from topic, teacher, certificate, and course_student tables.";
        }
        throw new BusinessException("Unsuccessful Deletion.");
    }

    @Transactional
    public List<CourseDTO> getAStudentsCourses(int studentId){
        Optional<Student> findStudent = studentRepo.findById(studentId);

        if(findStudent.isPresent()){
            
            throw new BusinessException("There is a student with id: "+studentId+", but, this student is not registered in any courses.");
        }
        throw new BusinessException("There is no student with the id: "+studentId);
    }
}
