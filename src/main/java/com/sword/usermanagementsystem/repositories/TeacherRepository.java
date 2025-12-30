package com.sword.usermanagementsystem.repositories;

import com.sword.usermanagementsystem.entities.Teacher;
import com.sword.usermanagementsystem.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Integer> { /*primary key is of type Integer, that's why it's listed next to the Teacher entity*/
    @Modifying
    @Query(value = "DELETE FROM teacher_topic WHERE topic_id = :topicId", nativeQuery = true)
    void deleteTeacherTopicLinks(int topicId);

}
