package com.store.checkout.service.controllers;

import com.store.checkout.service.domain.User;
import com.store.checkout.service.security.dtos.AuthorizationRequest;
import com.store.checkout.service.security.dtos.UserResponse;
import com.store.checkout.service.services.UserService;
import com.store.checkout.service.services.mappers.UserMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/users")
    public ResponseEntity<String> saveUser(@RequestBody AuthorizationRequest userRequest) {
        userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        userService.save(userMapper.toDomain(userRequest));
        return new ResponseEntity<>(userRequest.getUserName(), HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserResponse> getUser(@PathVariable long id) {
        final User user = userService.getUser(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        UserResponse userResponse = userMapper.toResponse(user);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<User> users = userService.getAll();
        if (users == null) {
            return ResponseEntity.notFound().build();
        }
        List<UserResponse> userResponses = userMapper.usersToResponse(users);
        return new ResponseEntity<>(userResponses, HttpStatus.OK);
    }

}
