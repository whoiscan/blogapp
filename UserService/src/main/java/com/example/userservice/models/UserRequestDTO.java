package com.example.userservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {
    @NotBlank
    private String username;
    @Size(max = 20)
    private String firstname;
    @Size(max = 20)
    private String lastname;
    @NotBlank
    private String password;
    @NotBlank
    private String roleName;
}
