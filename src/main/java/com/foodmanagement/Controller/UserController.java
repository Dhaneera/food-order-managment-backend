package com.foodmanagement.Controller;

import com.foodmanagement.Entity.User;
import com.foodmanagement.Service.UsersService;
import com.foodmanagement.dto.UsersDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/students")
@RequiredArgsConstructor
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UsersService usersService;

    @PostMapping("/create")
    public ResponseEntity<User> createStudent(@RequestBody UsersDto user) {
        log.info("got the request from {}",user.toString());
        User createdStudent = usersService.addUser(user);
        return new ResponseEntity<>(createdStudent, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<User>> getAllStudents(Pageable pageable) {
        Page<User> students = usersService.getAllUsers(pageable);
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getStudentById(@PathVariable Long id) {
        Optional<User> student = usersService.getStudentById(id);
        if (student.isPresent()) {
            return new ResponseEntity<>(student.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateStudent(@PathVariable Long id, @RequestBody UsersDto userDetails) {
        Optional<User> studentOptional = usersService.getStudentById(id);
        if (studentOptional.isPresent()) {
            User updatedStudent = usersService.updateUser(id, userDetails);
            return new ResponseEntity<>(updatedStudent, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id) {
        boolean isDeleted = usersService.deleteUser(id);
        if (isDeleted) {
            return new ResponseEntity<String>("deleted Successfully",HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
