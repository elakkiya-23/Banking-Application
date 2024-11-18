package com.bank_app.controller;

import com.bank_app.dto.LoginRequest;
import com.bank_app.dto.UserDTO;
import com.bank_app.models.User;
import com.bank_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));  // Encrypt the password

        // Save the user
        User savedUser = userService.registerUser(user);

        // Return success response
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            // Use the loginUser method from the UserService to authenticate the user
            boolean isAuthenticated = userService.loginUser(loginRequest.getEmail(), loginRequest.getPassword());

            if (isAuthenticated) {
                // If authentication is successful, retrieve the user object
                Optional<User> userOptional = userService.findByEmail(loginRequest.getEmail());
                if (userOptional.isPresent()) {
                    User user = userOptional.get();

                    // Prepare the response data
                    Map<String, Object> response = new HashMap<>();
                    response.put("user", user);  // You may want to return only necessary user details
                    response.put("message", "Login successful! Redirecting to the dashboard...");

                    // Return response with user data and message
                    return ResponseEntity.ok(response);
                } else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                            .body(Map.of("message", "User not found."));
                }
            } else {
                // If authentication fails, return an error response
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("message", "Invalid email or password."));
            }
        } catch (Exception e) {
            // Catch any other exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Something went wrong. Please try again later."));
        }
    }

}
