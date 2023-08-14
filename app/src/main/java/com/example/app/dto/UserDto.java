package com.example.app.dto;

import com.example.app.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@AllArgsConstructor
public class UserDto {
    private long id;
    private String userName;
    private String password;

}
