package com.example.app.dto;

import com.example.app.model.Role;
import lombok.Data;

import java.util.List;

@Data
public class RegistrationUserDto {
    private long id;
    private String userName;

    private String password;
    private String confirmPassword;

    private String firstName;

    private String lastName;
    private List<Role> roleList;
}
