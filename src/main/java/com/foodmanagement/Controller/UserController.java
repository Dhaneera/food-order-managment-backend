package com.foodmanagement.Controller;


import com.foodmanagement.Entity.User;
import com.foodmanagement.Service.UsersService;
import com.foodmanagement.dto.UsersDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/api")
public class UserController {

    @Autowired
    private UsersService userService;

    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody UsersDto userDto) {
        User user = userService.addUser(userDto);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody UsersDto userDto) {
        User user = userService.updateUser(id, userDto);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<UsersDto>> getAllUsers() {
        List<UsersDto> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
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