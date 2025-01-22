package com.foodmanagement.Controller;


import com.foodmanagement.Entity.User;
import com.foodmanagement.Service.UsersService;
import com.foodmanagement.dto.GetUserByStatusDto;
import com.foodmanagement.dto.UsersDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/type")
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

    @GetMapping("/getAll")
    public Page<User> getAllUsers(Pageable page) {
        Page users= userService.getAllUsers(page);
        log.info("users : "+users.toString());
        return users;
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
}