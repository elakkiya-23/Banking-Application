package com.bank_app.service;

import com.bank_app.dto.LoginRequest;
import com.bank_app.dto.LoginResponse;
import com.bank_app.models.User;
import com.bank_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Method to register a new user
    public User registerUser(User user) {
        // Save the password as plain text (not recommended for production)
        return userRepository.save(user);
    }

    // Method to handle login
    public LoginResponse login(LoginRequest loginRequest) {
        System.out.println("Attempting login for email: " + loginRequest.getEmail());

        Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());
        if (userOptional.isEmpty()) {
            System.out.println("User not found for email: " + loginRequest.getEmail());
            throw new IllegalArgumentException("Invalid email or password");
        }

        User user = userOptional.get();
        System.out.println("User found: " + user.getEmail());
        System.out.println("Stored password: " + user.getPassword());

        // Compare plain-text passwords
        if (!loginRequest.getPassword().equals(user.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        return new LoginResponse("Login successful", user.getFirst_name());
    }
}
