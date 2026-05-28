package com.learning.SpringSecurity312.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username", unique = true)
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
    @Column(name = "password")
    @Size(min = 2, message="Password should be greater than 1")
    private String password;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="users_role")
    private List<Role> roles;

    public User(String username, String lastname, int age) {
        this.username = username;
        this.lastname = lastname;
        this.age = age;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public User() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
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
