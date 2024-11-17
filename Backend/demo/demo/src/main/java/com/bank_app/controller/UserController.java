
package com.bank_app.controller;

import com.bank_app.models.User;
import com.bank_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;  // Inject UserService

    // Register a new user
    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@Valid @RequestBody User user, BindingResult bindingResult) {
        // Check if there are validation errors
        if (bindingResult.hasErrors()) {
            // If errors, return the validation errors to the frontend
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage()) // Extract error messages
                    .toList();

            return ResponseEntity.badRequest().body(errors); // Send back the errors with 400 Bad Request
        }

        // Save the user using the service
        User registeredUser = userService.registerUser(user);  // Call the instance method of userService

        // Return success response
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED); // Return the registered user
    }
}
