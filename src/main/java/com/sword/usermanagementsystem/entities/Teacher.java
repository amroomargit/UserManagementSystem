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

    @Column(name = "name")
    private String name;

    //One teacher to many events
    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true /*,fetch = FetchType.LAZY or /*,fetch = FetchType.EAGER*/)
    private List<Event> events;


    /*The infinite loop happens because of how Java objects reference each other in memory when converting to JSON, not
    because of the foreign key itself.*/


    /*
    @ManyToOne → EAGER by default (JPA will fetch the Teacher when the Event is loaded)
    @OneToMany → LAZY by default (JPA will NOT fetch events when Teacher is loaded, unless accessed).

    If Teacher.events is LAZY and you try to access teacher.getEvents() outside a transaction (e.g., in controller
    after session closed), you'll get LazyInitializationException. To avoid this you can Use DTOs and write a query
    that returns DTOs directly (this is the option we went with)

    If Event.teacher is EAGER (default) and you serialize Event entity to JSON, the teacher will be pulled immediately

    */
}
