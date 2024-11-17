package com.bank_app.service;

import com.bank_app.models.User;
import com.bank_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // Method to save user to the database
    public User saveUser(User user) {
        // Generate token and code
        String token = UUID.randomUUID().toString(); // For example, a random UUID as token
        String code = UUID.randomUUID().toString().substring(0, 6); // Random 6 character code (e.g., for email verification)

        // Set the token and code
        user.setToken(token);
        user.setCode(code);
        // Save the user to the database
        return userRepository.save(user);
    }
    public User registerUser(User user) {
        // Encrypt the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Save the user to the database
        return userRepository.save(user);
    }
}