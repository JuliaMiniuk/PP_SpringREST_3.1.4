package com.learning.SpringSecurity312.dao;



import com.learning.SpringSecurity312.model.User;

import java.util.List;

public interface UserDao {
    List<User> getAllUsers();
    void saveUser(User user);
    void deleteUser(int id);
    User getUser(int id);

}
