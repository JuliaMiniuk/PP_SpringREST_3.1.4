package com.learning.SpringSecurity312.controller;

import com.learning.SpringSecurity312.model.User;
import com.learning.SpringSecurity312.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/")
    public String showAllUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "all-users";
    }
    @GetMapping(value = "/new")
    public String addNewUser(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "user-info";
    }
    @PostMapping(value = "/save")
    public String saveUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "user-info";
        }
        userService.saveUser(user);
        return "redirect:/";
    }
    @PostMapping(value = "/delete")
    public String deleteUser(@RequestParam("id") int id){
        userService.deleteUser(id);
        return "redirect:/";
    }
    @GetMapping(value= "/update")
    public String updateUser(@RequestParam("id") int id, Model model) {
        User user = userService.getUser(id);
        model.addAttribute("user", user);
        return "user-info";
    }
}
