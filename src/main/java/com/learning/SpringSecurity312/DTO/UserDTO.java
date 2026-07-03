package com.learning.SpringSecurity312.DTO;

import com.learning.SpringSecurity312.model.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {
    private Long  id;
    private String username;
    private String lastname;
    private int age;
    private List<Long> roleIds;
    private List<String> roleNames;

    public UserDTO(String username, String lastname, int age, List<Long> roleIds, List<String> roleNames) {
        this.username = username;
        this.lastname = lastname;
        this.age = age;
        this.roleIds = roleIds;
        this.roleNames = roleNames;
    }
}
