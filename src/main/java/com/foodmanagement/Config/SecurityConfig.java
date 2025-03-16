package com.foodmanagement.Config;

import com.foodmanagement.Service.impl.CustomUserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled=true)
public class SecurityConfig {

    private final JwtAuthEntryPoint jwtAuthEntryPoint;
    private final CustomUserDetailServiceImpl customUserDetailServiceImpl;
    private final CorsConfig corsConfigurationSource;



    @Autowired
    public SecurityConfig(CustomUserDetailServiceImpl customUserDetailServiceImpl, JwtAuthEntryPoint jwtAuthEntryPoint, CorsConfig corsConfigurationSource) {
        this.customUserDetailServiceImpl = customUserDetailServiceImpl;
        this.jwtAuthEntryPoint=jwtAuthEntryPoint;
        this.corsConfigurationSource = corsConfigurationSource;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors().configurationSource(corsConfigurationSource.corsConfigurationSource()).and().csrf(csrf -> csrf.disable())
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling.authenticationEntryPoint(jwtAuthEntryPoint))
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/api/auth/**").permitAll() // Allow all endpoints under /api/auth/
                                .requestMatchers("/api/auth/register").permitAll() // Allow /register without JWT
                                .requestMatchers("/api/auth/login").permitAll() // Allow /register without JWT
                                .requestMatchers("/api/orders/**").permitAll()
                                .requestMatchers("/api/orders/createdBy/{createdBy}").permitAll()
                                .requestMatchers("/api/users/**").permitAll()
                                .requestMatchers("/api/users/type{type}").permitAll()
                                .requestMatchers("/api/images/**").permitAll()
                                .requestMatchers("/api/images/upload").permitAll()
                                .requestMatchers("/api/images//{id}").permitAll()
                                .requestMatchers("/api/image/base64/{id}").permitAll()
                                .requestMatchers("/meal/**").permitAll()
                                .requestMatchers("/api/payments/**").permitAll()
                                .requestMatchers("/api/Mailing/**").permitAll()
                                .requestMatchers("/send-otp").permitAll()
                                .requestMatchers("/api/Mailing/check-mail").permitAll()
                                .requestMatchers("/api/users/resetPassword").permitAll()
                                .requestMatchers("/api/users/getAllEmployees/{role}/{pageNum}").permitAll()
                                .requestMatchers("/api/users/type").permitAll()
                                .anyRequest().authenticated()
                )
                .httpBasic(withDefaults()); // Enable HTTP Basic authentication if needed

        // Add JWT filter but exclude /register route
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(){
        return new JwtAuthenticationFilter();
    }
}