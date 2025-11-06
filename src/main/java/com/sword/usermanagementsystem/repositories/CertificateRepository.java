package com.sword.usermanagementsystem.repositories;

import com.sword.usermanagementsystem.entities.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CertificateRepository extends JpaRepository<Certificate,Integer> {
}
