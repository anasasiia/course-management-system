package com.example.app.controller;

import com.example.app.dto.LoginDto;
import com.example.app.dto.RegistrationUserDto;
import com.example.app.service.AuthService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        return "login";
    }


    @PostMapping("/login")
    public ResponseEntity<?> createAuthToken(@RequestBody LoginDto loginDto) {
        return authService.createAuthToken(loginDto);
    }

    @PostMapping("/users/new")
    public ResponseEntity<?> createNewUser(@RequestBody RegistrationUserDto registrationUserDto) {
        return authService.createNewUser(registrationUserDto);
    }
}
