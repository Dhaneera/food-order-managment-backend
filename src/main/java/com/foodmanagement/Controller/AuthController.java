package com.foodmanagement.Controller;

import com.foodmanagement.Config.JwtTokenGenerator;
import com.foodmanagement.Config.SecurityConstance;
import com.foodmanagement.Entity.Role;
import com.foodmanagement.Entity.User;
import com.foodmanagement.Repository.RoleRepository;
import com.foodmanagement.Repository.UsersRepository;
import com.foodmanagement.Service.impl.CustomUserDetailServiceImpl;
import com.foodmanagement.dto.AuthResponseDto;
import com.foodmanagement.dto.LoginDto;
import com.foodmanagement.dto.RegisterDto;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UsersRepository usersRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenGenerator jwtTokenGenerator;

    private CustomUserDetailServiceImpl customUserDetailService;


    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UsersRepository usersRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtTokenGenerator jwtTokenGenerator) {
        this.authenticationManager = authenticationManager;
        this.usersRepository = usersRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenGenerator=jwtTokenGenerator;
    }





    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto>   login(@RequestBody LoginDto loginDto, HttpServletResponse response) {

        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authenticate);

        String accessToken = jwtTokenGenerator.generateAccessToken(authenticate);
        String refreshToken = jwtTokenGenerator.generateRefreshToken(authenticate);


        User user = usersRepository.findByUsername(loginDto.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));


        List<String> roles = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toList());


        Long roleId = user.getRoles().isEmpty() ? null : user.getRoles().get(0).getId();


        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge((int) SecurityConstance.JWT_REFRESH_EXPIRATION / 1000);
        response.addCookie(refreshTokenCookie);

        // Return the response including userId, roleId, and tokens
        return new ResponseEntity<>(new AuthResponseDto(accessToken, refreshToken, roles, user.getId(), roleId), HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {

        Cookie refreshTokenCookie = new Cookie("refreshToken", null);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(0);
        response.addCookie(refreshTokenCookie);


        return ResponseEntity.ok("Logged out successfully");
    }


    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        // Check if the username already exists
        if (usersRepository.existsByUsername(registerDto.getUsername())) {
            return new ResponseEntity<>("User name is taken", HttpStatus.BAD_REQUEST);
        }


        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setName(registerDto.getName());
        user.setPhoneNumber(registerDto.getPhoneNumber());
        user.setMail(registerDto.getMail());
        user.setStatus("ACTIVE");
        user.setCreatedBy("System");
        user.setUpdatedBy("System");
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        // Get or create the appropriate role
        Role role;
        switch (registerDto.getRoleType().toLowerCase()) {
            case "staff":
                role = roleRepository.findByName("ROLE_STAFF");
                if (role == null) {
                    role = new Role();
                    role.setName("ROLE_STAFF");
                    roleRepository.save(role);
                }
                break;
            case "admin":
                // Ensure that admin role exists
                role = roleRepository.findByName("ROLE_ADMIN");
                if (role == null) {
                    role = new Role();
                    role.setName("ROLE_ADMIN");
                    roleRepository.save(role);
                }
                break;
            case "student":
                role = roleRepository.findByName("ROLE_STUDENT");
                if (role == null) {
                    role = new Role();
                    role.setName("ROLE_STUDENT");
                    roleRepository.save(role);
                }
                break;
            default:
                return new ResponseEntity<>("Invalid role type", HttpStatus.BAD_REQUEST);
        }

        // Set the role for the user
        user.setRoles(Collections.singletonList(role));

        // Save the user to the repository
        usersRepository.save(user);

        return new ResponseEntity<>("User registered successfully!", HttpStatus.OK);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthResponseDto> refreshToken(@CookieValue(value = "refreshToken", defaultValue = "") String refreshToken) {

        if (jwtTokenGenerator.validateToken(refreshToken)) {
            String username = jwtTokenGenerator.getUsernameFromJWT(refreshToken);
            Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList());
            String newAccessToken = jwtTokenGenerator.generateAccessToken(authentication);
            User user = usersRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            List<String> roles = user.getRoles().stream()
                    .map(Role::getName)
                    .collect(Collectors.toList());
            Long roleId = user.getRoles().isEmpty() ? null : user.getRoles().get(0).getId();  // Assuming single role per user

            return new ResponseEntity<>(new AuthResponseDto(newAccessToken, refreshToken, roles, user.getId(), roleId), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

}