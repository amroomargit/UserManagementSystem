package com.sword.usermanagementsystem.repositories;

import com.sword.usermanagementsystem.entities.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
    /*primary key is of type Integer, that's why it's listed next to the Teacher entity*/
}
