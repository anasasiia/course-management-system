package com.example.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class LoginDto {
    private String email;
    private String password;
}
