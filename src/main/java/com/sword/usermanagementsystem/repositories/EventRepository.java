package com.sword.usermanagementsystem.repositories;

import com.sword.usermanagementsystem.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
    /*primary key is of type Integer, that's why it's listed next to the Event entity */
}
