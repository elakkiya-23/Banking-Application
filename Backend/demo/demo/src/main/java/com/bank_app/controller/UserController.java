package com.bank_app.controller;

import com.bank_app.dto.LoginRequest;
import com.bank_app.dto.LoginResponse;
import com.bank_app.dto.UserDTO;
import com.bank_app.models.User;
import com.bank_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserService userService;

    // Endpoint to register a new user
    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult) {
        // Validate the user data
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errors);
        }

        // Convert UserDTO to User entity
        User user = new User();
        user.setFirst_name(userDTO.getFirst_name());
        user.setLast_name(userDTO.getLast_name());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword()); // Store the password as plain text

        // Save the user
        User savedUser = userService.registerUser(user);

        // Return success response
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    // Endpoint to log in a user
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        try {
            LoginResponse response = userService.login(loginRequest);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new LoginResponse(e.getMessage(), null));
        }
    }
}
