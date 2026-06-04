package com.learning.SpringSecurity312.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;


import java.util.Set;

@Entity
@Table(name="roles")
@Getter
@Setter
@NoArgsConstructor
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="name")
    private String name;
//    @ManyToMany(mappedBy = "roles")
//    @JoinTable(name = "user_roles"
//            , joinColumns = @JoinColumn(name="role_id")
//            , inverseJoinColumns = @JoinColumn(name="user_id"))
//    private Set<User> users;

    @Override
    public String getAuthority() {
        return getName();
    }
}
