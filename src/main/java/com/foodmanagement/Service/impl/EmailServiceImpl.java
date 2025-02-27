package com.foodmanagement.Service.impl;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;

@Service
public class EmailServiceImpl {
    private final JavaMailSender mailSender;
    private final SecureRandom random = new SecureRandom();

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    public String generateOTP() {
        int otp = 100000 + random.nextInt(900000); // Generates a 6-digit number
        return String.valueOf(otp);
    }

    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("wickramasuriyashehan@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }

    public String sendOTP(String to) {
        String otp = generateOTP();
        String subject = "Your OTP Verification Code";
        String body = "Your OTP code is: " + otp + "\nThis code is valid for 5 minutes.";
        sendEmail(to, subject, body);
        return otp;
    }
}
