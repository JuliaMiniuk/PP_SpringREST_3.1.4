package com.learning.SpringSecurity312.controller;

import com.learning.SpringSecurity312.DTO.UserDTO;
import com.learning.SpringSecurity312.model.Role;
import com.learning.SpringSecurity312.model.User;
import com.learning.SpringSecurity312.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
public class NewUserController {

    public NewUserController() {

    }

    @GetMapping
    public ResponseEntity<UserDTO> getUser(@AuthenticationPrincipal User user) {
        UserDTO userDTO = convertToUserDTO(user);
        return ResponseEntity.ok(userDTO);
    }

    private UserDTO convertToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setLastname(user.getLastname());
        userDTO.setAge(user.getAge());
        List<Long> roleIds = user.getRoles().stream().map(role -> role.getId()).collect(Collectors.toList());
        userDTO.setRoleIds(roleIds);
        List<String> roleNames = user.getRoles().stream().map(Role::getName).collect(Collectors.toList());
        userDTO.setRoleNames(roleNames);
        return userDTO;
    }
}
