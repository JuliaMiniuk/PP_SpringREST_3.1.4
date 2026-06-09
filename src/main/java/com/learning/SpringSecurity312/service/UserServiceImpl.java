package com.learning.SpringSecurity312.service;

import com.learning.SpringSecurity312.dao.RoleRepository;
import com.learning.SpringSecurity312.dao.UserRepository;
import com.learning.SpringSecurity312.model.Role;
import com.learning.SpringSecurity312.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    @PersistenceContext
    private EntityManager em;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final RoleService roleService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder bCryptPasswordEncoder, RoleService roleService, RoleService roleService1) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleService = roleService1;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public User saveUser(User user, List<Long> roleIds) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalArgumentException("User already exists: " + user.getUsername());
        }
        if (roleIds == null || roleIds.isEmpty()) {
            throw new IllegalArgumentException("Role Ids cannot be empty");
        }
        List<Role> roles = roleRepository.findAllById(roleIds);
        if (roles.size() != roleIds.size()) {
            throw new IllegalArgumentException("Role Ids do not match");
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.getRoles().addAll(roles);
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void updateUser(User user, Long id, List<Long> roleIds) {
        User oldUser = userRepository.findById(id).orElseThrow(() ->
                new UsernameNotFoundException("User not found with id: " + id));
        oldUser.setUsername(user.getUsername());
        oldUser.setLastname(user.getLastname());
        oldUser.setAge(user.getAge());
        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            oldUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }
        if(roleIds != null || !roleIds.isEmpty()) {
            List<Role> roles = roleRepository.findAllById(roleIds);
            if (roles.size() != roleIds.size()) {
                throw new IllegalArgumentException("Role Ids do not match");
            }
            oldUser.getRoles().clear();
            oldUser.getRoles().addAll(roles);
        }
        userRepository.save(oldUser);
    }


    @Override
    @Transactional
    public boolean deleteUser(Long id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public User getUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
    }

}
