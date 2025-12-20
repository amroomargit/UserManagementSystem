package com.sword.usermanagementsystem.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/*Note that we create the table in the code, but we add rows to the tables and fill in values in the db browser
(i.e. DataGrip, PgAdmin, etc.), not the code */

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "starttime")
    private LocalDateTime starttime;

    @Column(name = "endtime")
    private LocalDateTime endtime;

    //ManyToOne between course and teacher
    @ManyToOne
    @JoinColumn(name = "teacherid", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_course_teacher", foreignKeyDefinition = "FOREIGN KEY (teacherid) REFERENCES teacher(id) ON DELETE SET NULL") /*,fetch = FetchType.LAZY or /*,fetch = FetchType.EAGER*/) //The JoinColumn connects primary and foreign keys
    private Teacher teacher;

    //ManyToOne between course and topic
    @ManyToOne /*No cascading here because it causes an issue when deleting a teacher if there is a row in course table
    that links teacher, topic and course, causing the topic to delete from its own table entirely. This only happens
    with ManyToMany relationships */
    @JoinColumn(name = "topicid", referencedColumnName = "id")
    private Topic topic;
    //Cascading allowed on child side though, so there is in topic entity class

    //ManyToMany between course and student
    @ManyToMany //mappedBy should only be on one side (the inverse side, so Course class)
    @JoinTable(name = "course_student", joinColumns = @JoinColumn(name = "courseid") /*FK to course*/, inverseJoinColumns = @JoinColumn(name = "studentid")/*FK to student*/)
    private List<Student> students;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Certificate> certificates;

}
