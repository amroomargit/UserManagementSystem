package com.sword.usermanagementsystem.services;

import com.sword.usermanagementsystem.dtos.*;
import com.sword.usermanagementsystem.entities.*;
import com.sword.usermanagementsystem.exceptions.BusinessException;
import com.sword.usermanagementsystem.mappers.AdminMapper;
import com.sword.usermanagementsystem.mappers.StudentMapper;
import com.sword.usermanagementsystem.mappers.TeacherMapper;
import com.sword.usermanagementsystem.mappers.UserMapper;
import com.sword.usermanagementsystem.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserRepository userRepo;

    @Autowired
    UserMapper userMapper;

    @Autowired
    StudentMapper studentMapper;

    @Autowired
    StudentRepository studentRepo;

    @Autowired
    TeacherMapper teacherMapper;

    @Autowired
    TeacherRepository teacherRepo;

    @Autowired
    AdminRepository adminRepo;

    @Autowired
    AdminMapper adminMapper;

    @Autowired
    CourseRepository courseRepo;

    @Autowired
    CertificateRepository certificateRepo;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Transactional
    public List<UserDTO> getAllUsers(){
        return userRepo.findAll().stream().map(user -> {
            UserDTO dto = userMapper.toDTO(user);
            dto.setStudentEntity(studentMapper.toDTO(user.getStudent()));
            dto.setTeacherEntity(teacherMapper.toDTO(user.getTeacher()));
            return dto;
        }).collect(Collectors.toList());
    }


    @Transactional
    public StudentDTO studentRegistration(StudentDTO studentDTO){

        //check if username is already taken
        if(userRepo.findByUsername(studentDTO.getUsername()).isPresent()){
            throw new BusinessException("Username already taken.");
            /*No loop prompting for another attempt because Spring Boot REST API is stateless, no ongoing input/output
            loop. Backend should not be waiting for another input, instead, the frontend will try a new prompt.*/
        }

        //Encode password (one-way hash & salt using the BCrypt algorithm) (check Google Doc for more info)
        String encodedPassword = passwordEncoder.encode(studentDTO.getPassword());
        studentDTO.setPassword(encodedPassword);

        //Save to database
        User userEntity = userMapper.toEntity(studentDTO); //StudentDTO class inherits UserDTO class, so a StudentDTO object counts as a UserDTO, and can be saved as a User Entity, so we can enter it into the User Repo later
        userEntity.setRole("ROLE_STUDENT"); //Setting role to student for access to certain endpoints
        userRepo.save(userEntity); //Converted from DTO to Entity, so we can save in the repo

        Student studentEntity = studentEntityHelper(studentDTO);

        studentEntity.setUser(userEntity); //Able to setUser because user is an instance variable in the Student class from when we made the OneToOne relationship with User class
        studentRepo.save(studentEntity); //Save into student repo as well

        return studentDTO;
    }

    //Separate helper method because we use it in insertStudent in StudentService as well
    public Student studentEntityHelper(StudentDTO studentDTO){

        Student studentEntity = studentMapper.toEntity(studentDTO);

        //Reading courses that the student is registered in, and saving those so that they show up in the database jointable
        List<Course> courseEntities = new ArrayList<Course>();
        if(studentDTO.getCourseList() != null){ //if there are any courses to be registered in, in the first place
            for(CourseDTO dto : studentDTO.getCourseList()){
                Course c = courseRepo.findById(dto.getId()).orElseThrow(()->new BusinessException("Course not found: "+dto.getId()));
                c.getStudents().add(studentEntity);
                courseEntities.add(c);
            }
            studentEntity.setCourses(courseEntities);
        }

        List<Certificate> certificateEntities = new ArrayList<Certificate>();
        if(studentDTO.getCertificateList() != null){ //if there are any courses to be registered in, in the first place
            for(CertificateDTO dto: studentDTO.getCertificateList()){
                Certificate c = new Certificate();
                c.setGrade(dto.getGrade());
                c.setCertificateType(dto.getCertificateType());

                if(dto.getCourse() != null && dto.getCourse().getId() != 0){
                    Course course = courseRepo.findById(dto.getCourse().getId()).orElseThrow(() -> new BusinessException(
                            "Course not found: " + dto.getCourse().getId()));
                    c.setCourse(course);
                }

                c.setStudent(studentEntity);
                certificateEntities.add(c);
            }
            studentEntity.setCertificates(certificateEntities);
        }

        return studentEntity;
    }


    @Transactional
    public UserDTO login(String username, String rawPassword){

        //Check if entered username is present in database
        Optional<User> userOpt = userRepo.findByUsername(username);
        if(userOpt.isEmpty()){
            return null;
        }

        //Compare raw password with stored hash
        /*We pulled the UserDTO (which is what we stored at the end of the studentRegistration method) from the
        database when we called userOpt above to check if the username is present, so here, we are getting the password from
        that UserDTO where we found the username stored in*/
        String storedHash = userOpt.get().getPassword();
        boolean var =  passwordEncoder.matches(rawPassword,storedHash);

        if(var == false){
            return null;
        }

        UserDTO dto = userMapper.toDTO(userOpt.get());
        if (userOpt.get().getStudent() != null) {
            StudentDTO studentDTO = studentMapper.toDTO(userOpt.get().getStudent());
            studentDTO.setUsername(dto.getUsername());
            return studentDTO;
        }
        if (userOpt.get().getTeacher() != null) {
            TeacherDTO teacherDTO = teacherMapper.toDTO(userOpt.get().getTeacher());
            teacherDTO.setUsername(dto.getUsername());
            return teacherDTO;
        }
        if (userOpt.get().getAdmin() != null) {
            AdminDTO adminDTO = adminMapper.toDTO(userOpt.get().getAdmin());
            adminDTO.setUsername(dto.getUsername());
            return adminDTO;
        }

        return dto;
    }


    @Transactional
    public TeacherDTO teacherRegistration(TeacherDTO teacherDTO){
        if(userRepo.findByUsername(teacherDTO.getUsername()).isPresent()){
            throw new BusinessException("Username taken.");
        }

        String encodedPassword = passwordEncoder.encode(teacherDTO.getPassword());
        teacherDTO.setPassword(encodedPassword);

        User userEntity = userMapper.toEntity(teacherDTO);
        userEntity.setRole("ROLE_TEACHER");
        userRepo.save(userEntity);

        Teacher teacherEntity = teacherMapper.toEntity(teacherDTO);
        teacherEntity.setUser(userEntity);





        teacherEntity = populateTeacherTopics(teacherEntity);

        teacherRepo.save(teacherEntity);

        return teacherDTO;
    }

    public Teacher assignCourses(Teacher teacher){
        List<Course> courses = teacher.getCourses();
        List<Integer> courseIds = new ArrayList<>();
        for(){

        }

        return teacher;
    }

    public Teacher populateTeacherTopics(Teacher teacher){
        if(teacher.getCourses() == null) {
            return teacher;
        }
        List<Topic> topics = teacher.getCourses().stream().map(Course::getTopic).filter(Objects::nonNull).distinct().toList();
        teacher.setTopics(topics);
        return teacher;
    }

    @Transactional
    public String adminRegistration(AdminDTO adminDTO){
        if(userRepo.findByUsername(adminDTO.getUsername()).isPresent()){
            return "Username Already Taken.";
        }

        String encodedPassword = passwordEncoder.encode(adminDTO.getPassword());
        adminDTO.setPassword(encodedPassword);

        User userEntity = userMapper.toEntity(adminDTO);
        userEntity.setRole("ROLE_ADMIN");
        userRepo.save(userEntity);

        Admin adminEntity = adminMapper.toEntity(adminDTO);
        adminEntity.setUser(userEntity);
        adminRepo.save(adminEntity);

        return "Admin Registered Successfully";
    }

    @Transactional
    public String changePassword(int userId, ChangePasswordDTO changePasswordDTO){
        Optional<User> currentUser = userRepo.findById(userId);

        if(passwordEncoder.matches(changePasswordDTO.getOldPassword(), currentUser.get().getPassword())){
            currentUser.get().setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
            return "Successful Password Change.";
        }
        return "Unsuccessful Password Change.";
    }
}
