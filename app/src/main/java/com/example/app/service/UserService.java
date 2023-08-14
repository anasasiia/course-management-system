package com.example.app.service;

import com.example.app.dto.RegistrationUserDto;
import com.example.app.model.User;

public interface UserService {
    User createUser(RegistrationUserDto registrationUserDto);
    User updateUser(long id, RegistrationUserDto registrationUserDto);
}
