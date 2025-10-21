package com.sword.usermanagementsystem.entities;

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

    @Column(name = "topicid")
    private int topicid;

    @Column(name = "starttime")
    private LocalDateTime starttime;

    @Column(name = "endtime")
    private LocalDateTime endtime;

    //Many courses to one teacher
    @ManyToOne(cascade = CascadeType.ALL)
    //JoinColumn connects primary and foreign keys
    @JoinColumn(name = "teacherid", referencedColumnName = "id" /*,fetch = FetchType.LAZY or /*,fetch = FetchType.EAGER*/)
    private Teacher teacher;

    @ManyToMany //mappedBy should only be on one side (the inverse side, so Topic class)
    @JoinTable(name = "course_topic", joinColumns = @JoinColumn(name = "courseid") /*FK to course*/, inverseJoinColumns = @JoinColumn(name = "topicid")/*FK to topic*/)
    private List<Topic> topics;

}
