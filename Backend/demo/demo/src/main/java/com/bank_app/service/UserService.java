package com.bank_app.service;

import com.bank_app.models.User;
import com.bank_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Method to register a new user
    public User registerUser(User user) {
        // Encrypt the user's password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Generate a unique token and code (optional)
        user.setToken(UUID.randomUUID().toString());
        user.setCode(UUID.randomUUID().toString().substring(0, 6));

        // Save the user to the database
        return userRepository.save(user);
    }

    // Method to fetch all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
