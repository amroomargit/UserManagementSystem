package com.sword.usermanagementsystem.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
/*Note that we create the table in the code, but we add rows to the tables and fill in values in the db browser
(i.e. DataGrip, PgAdmin, etc.), not the code */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "teacher")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "firstname")
    private String firstname; /*Change to last name and start naming teachers Mr.Smith or something.
    Find out how you can change code and if that will change table (it should because of auto update in application.yml).*
    See if it's more proper to change in the code or PgAdmin (start using PgAdmin instead of DataGrip and change the instructions
    in the Google doc*/

    //One teacher to many events
    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Event> events;
    //Make a new class called TeacherDto, the exact same as this one, but...
    //change list type from Event to EventDto (keep same name for list as events)
    //each Teacher in this list should be left null (try also to not leave null and see what happens, just to test it out)
    //also play around with eager loading, see which is better between it and lazy loading
    //if you have extra time, then, after mapping manually, try using mapstruct
    //research var in the TeacherController, it's a new type of variable
    //PLEASE DON'T FORGET TO ACTUALLY TEST IT THIS TIME (return to client)
}
