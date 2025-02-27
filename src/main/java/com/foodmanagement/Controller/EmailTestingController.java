package com.foodmanagement.Controller;

import com.foodmanagement.Entity.User;
import com.foodmanagement.Repository.UsersRepository;
import com.foodmanagement.Service.impl.EmailServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/Mailing")
public class EmailTestingController {
    private final EmailServiceImpl emailService;
    private final UsersRepository usersRepository;


    public EmailTestingController(EmailServiceImpl emailService, UsersRepository usersRepository) {
        this.emailService = emailService;
        this.usersRepository = usersRepository;
    }

    @GetMapping("/send-otp")
    public String sendOTP(@RequestParam String email) {
        String otp = emailService.sendOTP(email);
        return otp;
    }
    @GetMapping("/check-mail")
    public ResponseEntity<Optional<User>> checkMail(@RequestParam String mail){
        return ResponseEntity.ok(usersRepository.findByEmail(mail));
    }
}