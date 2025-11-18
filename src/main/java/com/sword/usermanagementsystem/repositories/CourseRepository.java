package com.sword.usermanagementsystem.repositories;

import com.sword.usermanagementsystem.entities.Course;
import com.sword.usermanagementsystem.entities.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
    /*primary key is of type Integer, that's why it's listed next to the Course entity */

    Optional<Course> findByName(String username);
}
