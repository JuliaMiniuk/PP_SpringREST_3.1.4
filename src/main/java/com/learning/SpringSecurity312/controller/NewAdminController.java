package com.learning.SpringSecurity312.controller;

import com.learning.SpringSecurity312.DTO.CreateUserRequest;
import com.learning.SpringSecurity312.DTO.UpdateUserRequest;
import com.learning.SpringSecurity312.DTO.UserDTO;
import com.learning.SpringSecurity312.model.Role;
import com.learning.SpringSecurity312.model.User;
import com.learning.SpringSecurity312.service.RoleService;
import com.learning.SpringSecurity312.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
public class NewAdminController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public NewAdminController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/users")
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers().stream().map(this::convertToUserDTO)
                .collect(Collectors.toList());
    }

    @PostMapping("/users")
    public ResponseEntity<UserDTO> saveUser(@RequestBody @Valid CreateUserRequest createUserRequest) {
        User user = userService.saveUser(convertToUser(createUserRequest), createUserRequest.getRoleIds());
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToUserDTO(user));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody @Valid UpdateUserRequest updateUserRequest) {
        User user = userService.updateUser(convertToUser(updateUserRequest), id, updateUserRequest.getRoleIds());
        return ResponseEntity.ok(convertToUserDTO(user));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        User user = userService.getUser(id);
        return ResponseEntity.ok(convertToUserDTO(user));
    }

    private User convertToUser(CreateUserRequest createUserRequest) {
        return modelMapper.map(createUserRequest, User.class);
    }
    private User convertToUser(UpdateUserRequest updateUserRequest) {
        return modelMapper.map(updateUserRequest, User.class);
    }

    private UserDTO convertToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setLastname(user.getLastname());
        userDTO.setAge(user.getAge());
        List<Long> roleIds = user.getRoles().stream().map(Role::getId).collect(Collectors.toList());
        userDTO.setRoleIds(roleIds);
        List<String> roleNames = user.getRoles().stream().map(Role::getName).collect(Collectors.toList());
        userDTO.setRoleNames(roleNames);
        return userDTO;
    }
}
