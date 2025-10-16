package com.sword.usermanagementsystem.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

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
    private LocalTime starttime; /*change type to LocalDateTime (drop or alter and then change type)
    (figure out how to change it in the code but have it not mess everything up like when it threw errors) */

    @Column(name = "endtime")
    private LocalTime endtime;/*change type to LocalDateTime (drop or alter and then change type)
    (figure out how to change it in the code but have it not mess everything up like when it threw errors) */

    //Many events to one teacher
    @ManyToOne
    @JoinColumn(name = "teacherid", referencedColumnName = "id") //add cascade
    private Teacher teacher;
    //Make a new class called EventDto, the exact same as this one, but...
    //change instance variable type from Teacher to TeacherDto (keep same name though for the variable as teacher)
    //each Event in this list should be left null (try also to not leave null and see what happens, just to test it out)
    //also play around with eager loading, see which is better between it and lazy loading
    //if you have extra time, then, after mapping manually, try using mapstruct
    //PLEASE DON'T FORGET TO ACTUALLY TEST IT THIS TIME (return to client)
}
