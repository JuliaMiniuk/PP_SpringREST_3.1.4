package com.learning.SpringSecurity312.DTO;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CreateUserRequest {

    @NotEmpty(message = "Name should not be empty")
    @Pattern(regexp = "^\\p{L}+$", message = "Name must contain only letters")
    private String username;

    @NotEmpty(message = "Lastname should not be empty")
    @Pattern(regexp = "^\\p{L}+$", message = "Lastname must contain only letters")
    private String lastname;

    @Min(value = 0, message = "Age should be greater than 0")
    private int age;

    private List<Long> roleIds;

    @NotBlank
    @Size(min = 2, message = "Password should be greater than 1")
    private String password;
}
