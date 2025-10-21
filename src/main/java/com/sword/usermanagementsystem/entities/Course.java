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

    @Column(name = "starttime")
    private LocalDateTime starttime;

    @Column(name = "endtime")
    private LocalDateTime endtime;

    //Many courses to one teacher
    @ManyToOne(cascade = CascadeType.ALL)
    //JoinColumn connects primary and foreign keys
    @JoinColumn(name = "teacherid", referencedColumnName = "id" /*,fetch = FetchType.LAZY or /*,fetch = FetchType.EAGER*/)
    private Teacher teacher;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "topicid", referencedColumnName = "id")
    private Topic topic;


}
