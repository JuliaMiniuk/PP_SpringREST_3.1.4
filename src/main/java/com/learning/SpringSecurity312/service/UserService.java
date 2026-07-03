package com.learning.SpringSecurity312.service;


import com.learning.SpringSecurity312.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User saveUser(User user, List<Long> roleIds);
    User updateUser(User user, Long id, List<Long> roleIds);
    void deleteUser(Long id);
    User getUser(Long id);

}
