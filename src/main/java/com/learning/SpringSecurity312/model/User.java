package com.learning.SpringSecurity312.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
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
    @Size(min = 2, message = "Password should be greater than 1")
    private String password;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles"
    , joinColumns = @JoinColumn(name="user_id")
    , inverseJoinColumns = @JoinColumn(name="role_id"))
    private Set<Role> roles = new HashSet<>();

    public User(String username, String lastname, int age) {
        this.username = username;
        this.lastname = lastname;
        this.age = age;
    }

    @Override
    @NullMarked
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }
    @Override
    @NullMarked
    public String getPassword() {
        return password;
    }
    @Override
    @NullMarked
    public String getUsername() {
        return username;
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
