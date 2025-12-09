package com.sword.usermanagementsystem.services;

import com.sword.usermanagementsystem.dtos.CourseDTO;
import com.sword.usermanagementsystem.dtos.StudentDTO;
import com.sword.usermanagementsystem.entities.Course;
import com.sword.usermanagementsystem.entities.Student;
import com.sword.usermanagementsystem.entities.Topic;
import com.sword.usermanagementsystem.exceptions.BusinessException;
import com.sword.usermanagementsystem.mappers.CourseMapper;
import com.sword.usermanagementsystem.mappers.StudentMapper;
import com.sword.usermanagementsystem.mappers.TopicMapper;
import com.sword.usermanagementsystem.repositories.CourseRepository;
import com.sword.usermanagementsystem.repositories.StudentRepository;
import com.sword.usermanagementsystem.repositories.TopicRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    TopicRepository topicRepo;

    @Autowired
    TopicMapper topicMapper;

    //ManyToMany between courses and students
    @Transactional
    public List<StudentDTO> getAllStudents() {
        var students = studentRepo.findAll();

        var result = new ArrayList<StudentDTO>();
        for (var student : students) {
            var courses = student.getCourses();
            var studentDTO = studentMapper.toDTO(student);
            studentDTO.setCourseList(new ArrayList<CourseDTO>());

            for (var course : courses) {
                var courseDTO = courseMapper.toDTO(course);
                studentDTO.getCourseList().add(courseDTO);

            }
            result.add(studentDTO);
        }

        return result;
    }

    /* Method that returns a StudentDTO by the id. The student repository has a built-in findById method, so we use that to find
    the student matching the id, then the student we found gets mapped into a DTO using the toDTO method we made in StudentMapper class */
    @Transactional
    public StudentDTO getStudentById(int id) {
        Student student = studentRepo.findById(id).orElseThrow(() -> new RuntimeException("Could not find a student with id: " + id));
        /* var optional = studentRepo.findById(id);
        optional.ifPresent((a)->{});
        if(optional.isPresent()){
            var student1 = optional.get();
        } */
        return studentMapper.toDTO(student);
    }

    @Transactional
    public StudentDTO insertStudent(StudentDTO studentDTO) {
        return service.studentRegistration(studentDTO);
    }

    @Transactional
    public StudentDTO updateStudentInfo(int studentId, StudentDTO studentDTO) {
        Optional<Student> findStudent = studentRepo.findById(studentId);

        if (findStudent.isPresent()) {
            findStudent.get().setFirstName(studentDTO.getFirstName());
            findStudent.get().setLastName(studentDTO.getLastName());
            return studentMapper.toDTO(findStudent.get());
        }

        throw new BusinessException("Unsuccessful Update.");
    }

    @Transactional
    public String deleteStudent(int studentId) {
        Student findStudent = studentRepo.findById(studentId).orElseThrow(() -> new BusinessException("Student not found"));

        for (Course c : findStudent.getCourses()) {
            c.getStudents().remove(findStudent);
        }

        studentRepo.deleteById(studentId);
        return "Student: " + studentId + " has been successfully deleted from student and users, certificate, course and course_student tables.";
        }



    /*There is no studentId in course or courseId in student, it's a many-to-many relationship so we cant do it the same
     * way we did with certificate, you have to do it through the course_student join table*/
    @Transactional
    public List<StudentDTO> getStudentsInACourse(int courseId) {

        Optional<Course> findCourse = courseRepo.findById(courseId);

        if (findCourse.isPresent()) {
            List<Student> studentsInTheCourse = findCourse.get().getStudents();

            if (studentsInTheCourse.isEmpty()) {
                throw new BusinessException("There is a course with id: " + courseId + " , but, there are no students registered in this course.");
            }

            return studentsInTheCourse.stream().map(studentMapper::toDTO).toList();
        }
        throw new BusinessException("There is no course with id: " + courseId);
    }

    @Transactional
    public List<CourseDTO> enrollStudentInACourse(int studentId, int courseId) {
        Student student = studentRepo.findById(studentId).orElseThrow(() -> new BusinessException("There is no student " + studentId));
        Course course = courseRepo.findById(courseId).orElseThrow(() -> new BusinessException("There is no course " + courseId));

        student.getCourses().add(course);
        course.getStudents().add(student);

        return student.getCourses().stream().map(courseMapper::toDTO).toList();
    }

    @Transactional
    public List<StudentDTO> getTopicsStudents (int topicId){
        Topic topic = topicRepo.findById(topicId).orElseThrow(() -> new BusinessException(String.format("There is no topic with id %d",topicId)));

        /* This gets a list of StudentDTOs of all the student in a topic, but since there is an indirect link between
         topic and student (we have to access topic via course), we do it this way */
        return topic.getCourses().stream().flatMap(course -> course.getStudents().stream()).map(studentMapper::toDTO).toList();
    }

    @Transactional
    public StudentDTO dropStudentOutOfCourse (int studentId, int courseId){
        Course course = courseRepo.findById(courseId).orElseThrow(()->new BusinessException(String.format("There is no course with id %d",courseId)));
        Student student = studentRepo.findById(studentId).orElseThrow(()->new BusinessException(String.format("There is no student with id %d",studentId)));

        //Have to remove the relationship from both sides because this is a many-to-many relationship
        boolean removedSideA = student.getCourses().removeIf(c -> c.getId() == courseId);
        boolean removedSideB = course.getStudents().removeIf(s -> s.getId() == studentId);


        if(!removedSideA && !removedSideB){
            throw new BusinessException(String.format("Student %d is not enrolled in course %d",studentId,courseId));
        }

        return studentMapper.toDTO(student);
    }
}
