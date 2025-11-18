package com.sword.usermanagementsystem.repositories;

import com.sword.usermanagementsystem.entities.Certificate;
import com.sword.usermanagementsystem.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CertificateRepository extends JpaRepository<Certificate,Integer> {
    Optional<Certificate> findByName(String username);
}
