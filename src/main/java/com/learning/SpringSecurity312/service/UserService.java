package com.learning.SpringSecurity312.service;



import com.learning.SpringSecurity312.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {
 List<User> getAllUsers();
 User saveUser(User user);
    void updateUser(User user, Long id);
    boolean deleteUser(Long id);
    User getUser(Long id);

}
