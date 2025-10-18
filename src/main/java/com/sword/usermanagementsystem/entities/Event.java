package com.sword.usermanagementsystem.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/*Note that we create the table in the code, but we add rows to the tables and fill in values in the db browser
(i.e. DataGrip, PgAdmin, etc.), not the code */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "courseid")
    private int courseid;

    @Column(name = "starttime")
    private LocalDateTime starttime;

    @Column(name = "endtime")
    private LocalDateTime endtime;

    //Many events to one teacher
    @ManyToOne(cascade = CascadeType.ALL)
    //JoinColumn connects primary and foreign keys
    @JoinColumn(name = "teacherid", referencedColumnName = "id" /*,fetch = FetchType.LAZY or /*,fetch = FetchType.EAGER*/)
    private Teacher teacher;

    @ManyToMany //mappedBy should only be on one side (the inverse side, so Course class)
    @JoinTable(name = "event_course", joinColumns = @JoinColumn(name = "eventid") /*FK to event*/, inverseJoinColumns = @JoinColumn(name = "courseid")/*FK to course*/)
    private List<Course> courses;

}
