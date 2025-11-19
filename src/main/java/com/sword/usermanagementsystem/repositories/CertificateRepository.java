package com.sword.usermanagementsystem.repositories;

import com.sword.usermanagementsystem.entities.Certificate;
import com.sword.usermanagementsystem.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate,Integer> {
}
