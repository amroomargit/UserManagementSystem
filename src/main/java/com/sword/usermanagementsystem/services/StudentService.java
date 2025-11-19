package com.sword.usermanagementsystem.services;

import com.sword.usermanagementsystem.dtos.CourseDTO;
import com.sword.usermanagementsystem.dtos.StudentDTO;
import com.sword.usermanagementsystem.entities.Course;
import com.sword.usermanagementsystem.entities.Student;
import com.sword.usermanagementsystem.exceptions.BusinessException;
import com.sword.usermanagementsystem.mappers.CourseMapper;
import com.sword.usermanagementsystem.mappers.StudentMapper;
import com.sword.usermanagementsystem.repositories.CourseRepository;
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

    @Autowired
    private CourseRepository courseRepo;

    //ManyToMany between courses and students
    @Transactional
    public List<StudentDTO> getAllStudents(){
        var students = studentRepo.findAll();

        var result = new ArrayList<StudentDTO>();
        for(var student:students){
            var courses = student.getCourses();
            var studentDTO = studentMapper.toDTO(student);
            studentDTO.setCourseList(new ArrayList<CourseDTO>());

            for(var course:courses){
                var courseDTO  = courseMapper.toDTO(course);
                studentDTO.getCourseList().add(courseDTO);

            }
            result.add(studentDTO);
        }

        return result;
    }

    /* Method that returns a StudentDTO by the id. The student repository has a built-in findById method, so we use that to find
    the student matching the id, then the student we found gets mapped into a DTO using the toDTO method we made in StudentMapper class */
    @Transactional
    public StudentDTO getStudentById(int id){
        Student student = studentRepo.findById(id).orElseThrow(() -> new RuntimeException("Could not find a student with id: "+id));
        /* var optional = studentRepo.findById(id);
        optional.ifPresent((a)->{});
        if(optional.isPresent()){
            var student1 = optional.get();
        } */
        return studentMapper.toDTO(student);
    }

    @Transactional
    public StudentDTO insertStudent(StudentDTO studentDTO){
        return service.studentRegistration(studentDTO);
    }

    @Transactional
    public StudentDTO updateStudentInfo(int studentId, StudentDTO studentDTO){
        Optional<Student> findStudent = studentRepo.findById(studentId);

        if(findStudent.isPresent()) {
            findStudent.get().setFirstname(studentDTO.getFirstname());
            findStudent.get().setLastname(studentDTO.getLastname());
            return studentMapper.toDTO(findStudent.get());
        }

        throw new BusinessException("Unsuccessful Update.");
    }

    @Transactional
    public String deleteStudent(int studentId){
        Optional<Student> findStudent = studentRepo.findById(studentId);
        if(findStudent.isPresent()){
            studentRepo.deleteById(studentId);
            return "Student: "+ studentId +" has been successfully deleted from student and users, certificate, course and course_student tables.";
        }
        throw new BusinessException("Unsuccessful Deletion.");
    }

    /*There is no studentId in course or courseId in student, it's a many to many relationship so we cant do it the same
    * way we did with certificate, we need to figure out a way to do it through the course_student jointable*/
    @Transactional
    public List<StudentDTO> getStudentsInACourse(int courseId){
        Optional<Course> findCourse = courseRepo.findById(courseId);

        if(findCourse.isPresent()){
            List<StudentDTO> studentsInCourse = new ArrayList<>();
            List<Student> findGivenCourseIdInStudentTable = studentRepo.findByCourse_Id(courseId);

            if(!findGivenCourseIdInStudentTable.isEmpty()){
                for(int i = 0 ; i<=findGivenCourseIdInStudentTable.size(); i++){
                    studentsInCourse.add(studentMapper.toDTO(findGivenCourseIdInStudentTable.get(i)));
                }
                return studentsInCourse;
            }
            throw new BusinessException("There is a course with id: "+courseId+" , but, there are no students registered in this course.");
        }
        throw new BusinessException("There is no course with id: "+courseId);
    }

}
