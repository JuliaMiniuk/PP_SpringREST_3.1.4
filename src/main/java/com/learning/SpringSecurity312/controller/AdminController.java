package com.learning.SpringSecurity312.controller;

import com.learning.SpringSecurity312.model.User;


import com.learning.SpringSecurity312.service.RoleService;
import com.learning.SpringSecurity312.service.UserService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;


    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }
    @GetMapping
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("roles", roleService.getAllRoles());
        return "all-users";
    }
    @GetMapping("/new")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.getAllRoles());
        return "user-info";
    }
    @PostMapping("/save")
    public String saveUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                           @RequestParam(value="roleIds", required = false) List<Long> roleIds, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", roleService.getAllRoles());
            return "user-info";
        }
        userService.saveUser(user, roleIds);
        return "redirect:/admin";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        model.addAttribute("roles", roleService.getAllRoles());
        return "user-info";
    }

    @PostMapping("/edit/{id}")
    public String updateUser(@PathVariable Long id, @Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model,
    @RequestParam(value="roleIds", required= false) List<Long> roleIds) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", roleService.getAllRoles());
            return "user-info";
        }
        userService.updateUser(user, id, roleIds);
        return "redirect:/admin";
    }

}
