package com.bank_app.service;

import com.bank_app.models.User;
import com.bank_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Inject PasswordEncoder

    // Method to register a new user
    public User registerUser(User user) {
        // Encrypt the password before saving the user
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);

        // Save the user with the hashed password
        return userRepository.save(user);
    }

    // Method to find a user by email (for login)
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email); // This will return an Optional<User>
    }

    // Method to verify user credentials during login
    public boolean loginUser(String email, String password) {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        // Check if user exists
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            // Compare the provided password with the stored encrypted password
            return passwordEncoder.matches(password, user.getPassword());
        }

        return false; // User not found or password mismatch
    }
}
