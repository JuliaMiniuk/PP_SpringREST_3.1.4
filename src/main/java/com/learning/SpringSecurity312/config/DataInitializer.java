package com.learning.SpringSecurity312.config;

import com.learning.SpringSecurity312.dao.RoleRepository;
import com.learning.SpringSecurity312.dao.UserRepository;
import com.learning.SpringSecurity312.model.Role;
import com.learning.SpringSecurity312.model.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.HashSet;

@Configuration
public class DataInitializer {
    @Bean
    CommandLineRunner init(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            Role admin = roleRepository.findByName("ROLE_ADMIN")
                    .orElseGet(() -> {
                        Role role = new Role();
                        role.setName("ROLE_ADMIN");
                        return roleRepository.save(role);
                    });
            roleRepository.findByName("ROLE_USER")
                    .orElseGet(() -> {
                        Role role = new Role();
                        role.setName("ROLE_USER");
                        return roleRepository.save(role);
                    });
            if (userRepository.findByUsername("admin").isEmpty()) {
                User userAdmin = new User();
                userAdmin.setUsername("admin");
                userAdmin.setLastname("admin");
                userAdmin.setAge(20);
                userAdmin.setPassword(passwordEncoder.encode("admin"));
                userAdmin.setRoles(new HashSet<>(Collections.singletonList(admin)));
                userRepository.save(userAdmin);
            }
        };
    }
}
