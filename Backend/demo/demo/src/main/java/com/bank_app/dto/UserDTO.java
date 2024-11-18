package com.bank_app.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class UserDTO {

    @NotEmpty(message = "The First name field cannot be empty")
    @Size(min = 3, message = "The First name field must be greater than 3 characters")
    private String first_name;

    @NotEmpty(message = "The Last name field cannot be empty")
    @Size(min = 1, message = "The Last name field must have at least 1 character")
    private String last_name;

    @Email(message = "Invalid email format")
    @NotEmpty(message = "Email cannot be empty")
    private String email;

    @NotEmpty(message = "Password cannot be empty")
    private String password;

    // Getters and setters
    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
