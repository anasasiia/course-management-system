package com.example.app.controller;

import com.example.app.dto.LoginDto;
import com.example.app.dto.RegistrationUserDto;
import com.example.app.service.AuthService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

//    @GetMapping("/login")
//    public String getLoginPage() {
//        return "login";
//    }


    @PostMapping("/login")
    public ResponseEntity<?> createAuthToken(@RequestBody LoginDto loginDto) {
        return authService.createAuthToken(loginDto);
    }

    @PostMapping("/users")
    public ResponseEntity<?> createNewUser(@RequestBody RegistrationUserDto registrationUserDto) {
        return authService.createNewUser(registrationUserDto);
    }
}
