package com.example.app.service;

import com.example.app.dto.LoginDto;
import com.example.app.dto.RegistrationUserDto;

import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<?> createAuthToken(LoginDto loginDto);
    ResponseEntity<?> createNewUser(RegistrationUserDto registrationUserDto);
}
