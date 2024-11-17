package com.bank_app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")  // Enable CORS for API endpoints
                .allowedOrigins("http://localhost:3000")  // Allow React to make requests
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // Allowed methods
                .allowCredentials(true);
    }
}
