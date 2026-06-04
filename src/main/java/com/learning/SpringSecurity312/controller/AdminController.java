package com.learning.SpringSecurity312.controller;

import com.learning.SpringSecurity312.model.User;
import com.learning.SpringSecurity312.service.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserServiceImpl userService;

    @Autowired
    public AdminController(UserServiceImpl userService) {
        this.userService = userService;
    }
    @GetMapping
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "all-users";
    }
    @GetMapping("/new")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        return "user-info";
    }
    @PostMapping("/save")
    public String saveUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "user-info";
        }
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
    @PostMapping("/update")
    public String updateUser(@ModelAttribute("user") @Valid User user, @RequestParam("id") Long id, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            return "user-info";
        }
        userService.updateUser(user, id);
        return "user-info";
    }

}
