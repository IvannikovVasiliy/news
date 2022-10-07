package com.example.news.repository;

import com.example.news.entity.ERole;
import com.example.news.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(ERole name);
}
