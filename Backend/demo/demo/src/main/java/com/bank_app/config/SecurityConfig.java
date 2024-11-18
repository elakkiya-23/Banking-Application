package com.bank_app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Updated method to disable CSRF
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/**").permitAll() // Allow unrestricted access to /api/** endpoints
                        .anyRequest().authenticated() // Require authentication for other endpoints
                )
                .cors(cors -> {}); // Enable CORS with settings from your WebConfig class

        return http.build();
    }
}
