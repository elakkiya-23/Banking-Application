package com.bank_app.dto;

public class LoginResponse {
    private String message;
    private String first_name;

    public LoginResponse(String message, String first_name) {
        this.message = message;
        this.first_name = first_name;
    }

    // Getters and setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFirstName() {
        return first_name;
    }

    public void setFirstName(String first_name) {
        this.first_name = first_name;
    }
}
