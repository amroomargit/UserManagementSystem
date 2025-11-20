package com.sword.usermanagementsystem.repositories;

import com.sword.usermanagementsystem.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {/*primary key is of type Integer, that's why it's listed next to the Student entity */
}
