package com.example.app.service;

import com.example.app.dto.LoginDto;
import com.example.app.dto.RegistrationUserDto;
import com.example.app.model.User;
import com.example.app.service.impl.UserServiceImpl;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<?> createAuthToken(LoginDto loginDto);
    ResponseEntity<?> createNewUser(RegistrationUserDto registrationUserDto);
}
