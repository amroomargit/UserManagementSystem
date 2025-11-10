package com.sword.usermanagementsystem.repositories;

import com.sword.usermanagementsystem.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
}
