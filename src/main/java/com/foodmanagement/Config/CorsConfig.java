package com.foodmanagement.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Allow all origins (you can restrict this to a specific domain)
        configuration.addAllowedOrigin("http://192.168.1.16:3000"); // Change to your frontend URL
        configuration.addAllowedMethod("*");  // Allow all HTTP methods (GET, POST, PUT, DELETE, etc.)
        configuration.addAllowedHeader("*");  // Allow all headers
        configuration.setAllowCredentials(true);  // Allow credentials like cookies and authorization headers
        configuration.setMaxAge(3600L);  // Cache preflight response for 1 hour

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);  // Apply the CORS configuration to all endpoints

        return source;
    }
}
