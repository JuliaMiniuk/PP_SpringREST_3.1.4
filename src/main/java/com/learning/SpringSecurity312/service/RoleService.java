package com.learning.SpringSecurity312.service;

import com.learning.SpringSecurity312.model.Role;

import java.util.List;

public interface RoleService {
    List<Role> getAllRoles();
    Role findById(Long id);
    Role findByName(String name);

}
