package com.learning.SpringSecurity312.controller;

import com.learning.SpringSecurity312.model.Role;
import com.learning.SpringSecurity312.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {
    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }


    @GetMapping
    public List<Role> getRoles() {
        return roleService.getAllRoles();
    }
}
