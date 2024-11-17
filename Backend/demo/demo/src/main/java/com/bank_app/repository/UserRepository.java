package com.bank_app.repository;


import com.bank_app.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    // You can define custom queries here if needed, e.g., for checking if an email already exists
}
