package com.foodmanagement.Controller;


import com.foodmanagement.Entity.User;
import com.foodmanagement.Service.UsersService;
import com.foodmanagement.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UsersService userService;

    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody UsersDto userDto) {
        User user = userService.addUser(userDto);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/type")
    Page getUserByUserType(@RequestBody GetUserByStatusDto getUserByStatusDto, Pageable pageable) {
        Page user=userService.getUserByUserType(getUserByStatusDto, pageable);
        log.info("user : "+user.toString());
        return user;
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody UsersDto userDto) {
        User user = userService.updateUser(id, userDto);
        return ResponseEntity.ok(user);
    }
    @PutMapping("status/change/{id}")
    public boolean updateUserStatus(@PathVariable Long id){
       return userService.updateStatus(id);
    }

    @GetMapping("/getAll")
    public Page<User> getAllUsers(Pageable page) {
        Page users= userService.getAllUsers(page);
        log.info("users : "+users.toString());
        return users;
    }

    @GetMapping("/getAll/students")
    public Page<User> getAllStudents(Pageable pageable) {
        Page <User> page= userService.getAllStudents(pageable);
        return page;
    }

    @GetMapping("/getAllEmployees")
    public PaginatedResponse<?> getAllEmployees(@RequestParam String page, @RequestParam String size) {
        return userService.getAllEmployees(page, size);
    }

    @GetMapping("/getAllStudents/{role}/{pageNum}")
    public PaginatedResponse<?> getAllStudents(@PathVariable String role, @PathVariable String pageNum) {
        return userService.getAllStudents(role, pageNum);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok("User deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
        }
    }
    @GetMapping("/serach")
    public ResponseEntity<Page<User>> searchUsersByUsername(@RequestParam String username,Pageable pageable) {
        Page<User> users = userService.searchUsersByUsername(username, pageable);
        return ResponseEntity.ok(users);
    }
    @GetMapping("/serachEmployees")
    public ResponseEntity<Page<User>> searchEmployeesByUsername(@RequestParam String username,Pageable pageable) {
        Page<User> users = userService.searchEmployeesByUsername(username, pageable);
        return ResponseEntity.ok(users);
    }
    @GetMapping("/getByUsername")
    public ResponseEntity<User> getUserById(@RequestParam String username) {
        Optional<User> user = userService.getUserByUsername(username);
        return user.map(ResponseEntity::ok)
               .orElse(ResponseEntity.status(404).build());
    }

    @PutMapping("/resetPassword")
    public ResponseEntity<String> resetPassword( @RequestBody UpdatePasswordDto updatePasswordDto) {
        try {
            userService.updatePasswordByMail(updatePasswordDto);
            return ResponseEntity.ok("Password reset successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
        }
    }

    @PutMapping("/{userId}/contact-info")
    public ResponseEntity<String> updateContactInfo(@PathVariable Long userId, @RequestBody UserContactUpdateRequest request) {
        userService.updateUserContactInfo(userId, request.getPhoneNumber(), request.getMail());
        return ResponseEntity.ok("User contact info updated successfully");
    }
    
}