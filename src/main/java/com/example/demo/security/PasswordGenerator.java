package com.example.demo.security;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordGenerator {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void generatePassword() {
        String rawPassword = "admin123";
        String encodedPassword = passwordEncoder.encode(rawPassword);
        System.out.println("암호화된 비밀번호: " + encodedPassword);
    }
}