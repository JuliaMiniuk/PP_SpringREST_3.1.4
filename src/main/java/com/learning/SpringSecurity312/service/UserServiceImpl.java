package com.learning.SpringSecurity312.service;

import com.learning.SpringSecurity312.dao.RoleRepository;
import com.learning.SpringSecurity312.dao.UserDao;
import com.learning.SpringSecurity312.dao.UserRepository;
import com.learning.SpringSecurity312.model.Role;
import com.learning.SpringSecurity312.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserDetailsService {
    @PersistenceContext
    private EntityManager em;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    @Transactional
    public User saveUser(User user) {
       if(userRepository.findByUsername(user.getUsername()).isPresent()) {
           throw new IllegalArgumentException("User already exists: " + user.getUsername());
       }
       user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
       Role userRole = roleRepository.findByName("ROLE_USER")
               .orElseThrow(() -> new RuntimeException("role not found"));
       user.setRoles(new ArrayList<>(List.of(userRole)));
       return userRepository.save(user);
    }


    @Transactional
    public boolean deleteUser(Long id) {
       if(userRepository.findById(id).isPresent()) {
           userRepository.deleteById(id);
           return true;
       }
       return false;
    }


    @Transactional(readOnly = true)
    public User getUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(new User());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }
}
