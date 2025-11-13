package com.sword.usermanagementsystem.services;

import com.sword.usermanagementsystem.dtos.CourseDTO;
import com.sword.usermanagementsystem.dtos.StudentDTO;
import com.sword.usermanagementsystem.entities.Course;
import com.sword.usermanagementsystem.entities.Student;
import com.sword.usermanagementsystem.exceptions.BusinessException;
import com.sword.usermanagementsystem.mappers.CourseMapper;
import com.sword.usermanagementsystem.mappers.StudentMapper;
import com.sword.usermanagementsystem.repositories.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepo;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    CourseMapper courseMapper;

    @Autowired
    UserService service;

    //ManyToMany between courses and students
    @Transactional
    public List<StudentDTO> getAllStudents(){
        var students = studentRepo.findAll();

        var result = new ArrayList<StudentDTO>();
        for(var student:students){
            var courses = student.getCourses();
            var studentDTO = studentMapper.toDTO(student);
            studentDTO.setCourses(new ArrayList<CourseDTO>());

            for(var course:courses){
                var courseDTO  = courseMapper.toDTO(course);
                studentDTO.getCourses().add(courseDTO);

            }
            result.add(studentDTO);
        }

        return result;
    }

    /* Method that returns a StudentDTO by the id. The student repository has a built-in findById method, so we use that to find
    the student matching the id, then the student we found gets mapped into a DTO using the toDTO method we made in StudentMapper class */
    public StudentDTO getStudentById(int id){
        Student student = studentRepo.findById(id).orElseThrow(() -> new RuntimeException("Could not find a student with id: "+id));
        /* var optional = studentRepo.findById(id);
        optional.ifPresent((a)->{});
        if(optional.isPresent()){
            var student1 = optional.get();
        } */
        return studentMapper.toDTO(student);
    }

    public StudentDTO insertStudent(StudentDTO studentDTO){
        return service.studentRegistration(studentDTO);
    }

    public StudentDTO updateStudentInfo(int studentId, StudentDTO studentDTO){
        Optional<Student> findStudent = studentRepo.findById(studentId);

        if(findStudent.isPresent()) {
            if (findStudent.get().getFirstname()!=null){
                findStudent.get().setFirstname(studentDTO.getFirstname());
            }

            if (findStudent.get().getLastname()!=null){
                findStudent.get().setLastname(studentDTO.getLastname());
            }

            if (findStudent.get().getCourses()!=null){

            }

            if (findStudent.get().getCertificates()!=null){

            }

            return studentMapper.toDTO(findStudent.get());
        }

        throw new BusinessException("Unsuccessful Update.");
    }

    public String deleteStudent(int studentId){
        Optional<Student> findStudent = studentRepo.findById(studentId);
        if(findStudent.isPresent()){
            studentRepo.deleteById(studentId);
            return "Student: "+ studentId +" has been successfully deleted from student and users, certificate, course and course_student tables.";
        }
        throw new BusinessException("Unsuccessful Deletion.");
    }

}
