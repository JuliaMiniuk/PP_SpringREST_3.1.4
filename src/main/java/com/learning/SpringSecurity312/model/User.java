package com.learning.SpringSecurity312.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "username")
    @NotEmpty(message = "Name should not be empty")
    @Pattern(regexp = "^\\p{L}+$", message = "Name must contain only letters")
    private String username;
    @Column(name = "lastname")
    @NotEmpty(message = "Lastname should not be empty")
    @Pattern(regexp = "^\\p{L}+$", message = "Lastname must contain only letters")
    private String lastname;
    @Column(name = "age")
    @Min(value = 0, message = "Age should be greater than 0")
    private int age;

    public User(String username, String lastname, int age) {
        this.username = username;
        this.lastname = lastname;
        this.age = age;
    }

    public User() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", lastname='" + lastname + '\'' +
                ", age=" + age +
                '}';
    }

}
