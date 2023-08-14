package com.example.app.controller;

import com.example.app.dto.RegistrationUserDto;
import com.example.app.dto.UserDto;
import com.example.app.model.User;
import com.example.app.repository.UserRepository;
import com.example.app.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static com.example.app.controller.UserController.USER_CONTROLLER_PATH;

@RestController
@AllArgsConstructor
@RequestMapping(USER_CONTROLLER_PATH)
public class UserController {
    public static final String USER_CONTROLLER_PATH = "/users";

    public static final String ID = "/id";

    private final UserRepository userRepository;
    private final UserService userService;

    @GetMapping()
    public List<User> getAll() {
        return userRepository.findAll().stream().toList();
    }

    @GetMapping(ID)
    public User getUserById(final @Valid @PathVariable long id) {
        return userRepository.getById(id);
    }

    @PostMapping
    public User createUser(final @Valid @RequestBody RegistrationUserDto registrationUserDto) {
        return userService.createUser(registrationUserDto);
    }

    @PutMapping(ID)
    public User updateUser(final @PathVariable long id,
                                                final @Valid @RequestBody RegistrationUserDto registrationUserDto) {
        return userService.updateUser(id, registrationUserDto);
    }

    @DeleteMapping(ID)
    public void deleteUser(final @Valid @PathVariable long id) {
        userRepository.deleteById(id);
    }
}
