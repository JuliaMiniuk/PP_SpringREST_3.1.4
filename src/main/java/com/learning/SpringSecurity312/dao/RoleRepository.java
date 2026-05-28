package com.learning.SpringSecurity312.dao;

import com.learning.SpringSecurity312.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
