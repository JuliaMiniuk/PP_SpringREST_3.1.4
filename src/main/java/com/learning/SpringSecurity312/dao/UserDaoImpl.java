package com.learning.SpringSecurity312.dao;

import com.learning.SpringSecurity312.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> getAllUsers() {
        List<User> users = entityManager.createQuery("from User", User.class).getResultList();
        return users;
    }

    @Override
    public void saveUser(User user) {
        if(user.getId()==0) {
            entityManager.persist(user);
        } else {
            entityManager.merge(user);
        }
    }

    @Override
    public void deleteUser(int id) {
        User user = entityManager.find(User.class, id);
        if (user != null) {
            entityManager.remove(user);
        }
    }

    @Override
    public User getUser(int id) {
        User user = entityManager.find(User.class, id);
        return user;
    }

}
