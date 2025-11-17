package com.sword.usermanagementsystem.repositories;

import com.sword.usermanagementsystem.entities.Topic;
import com.sword.usermanagementsystem.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TopicRepository extends JpaRepository<Topic,Integer> {
    /*primary key is of type Integer, that's why it's listed next to the Course entity */

    Optional<Topic> findByName(String username);
}
