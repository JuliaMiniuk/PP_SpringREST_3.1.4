package com.learning.SpringSecurity312.service;



import com.learning.SpringSecurity312.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User saveUser(User user);
    void deleteUser(int id);
    User getUser(int id);
}
