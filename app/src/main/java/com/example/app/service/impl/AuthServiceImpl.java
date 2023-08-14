package com.example.app.service.impl;

import com.example.app.component.JWTHelper;
import com.example.app.dto.LoginDto;
import com.example.app.dto.RegistrationUserDto;
import com.example.app.dto.UserDto;
import com.example.app.exceptions.AppError;
import com.example.app.model.User;
import com.example.app.service.AuthService;
import com.example.app.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserServiceImpl userService;
    private final JWTHelper jwtHelper;
    private final AuthenticationManager authenticationManager;
    @Override
    public ResponseEntity<?> createAuthToken(LoginDto loginDto) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(),
                    loginDto.getPassword()));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(),
                    "Wrong username or password"), HttpStatus.UNAUTHORIZED);
        }

        UserDetails userDetails = userService.loadUserByUsername(loginDto.getEmail());
        String token = jwtHelper.generateToken(userDetails);
        return ResponseEntity.ok(token);
    }

    @Override
    public ResponseEntity<?> createNewUser(RegistrationUserDto registrationUserDto) {
        if (!registrationUserDto.getPassword().equals(registrationUserDto.getConfirmPassword())) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Passwords are not equal"),
                    HttpStatus.BAD_REQUEST);
        }
        if (userService.findByUsername(registrationUserDto.getUserName()).isPresent()) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(),
                    "User with this username already exists"), HttpStatus.BAD_REQUEST);
        }
        User user = userService.createUser(registrationUserDto);
        return ResponseEntity.ok(new UserDto(user.getId(), user.getUserName(), user.getPassword()));
    }
}
