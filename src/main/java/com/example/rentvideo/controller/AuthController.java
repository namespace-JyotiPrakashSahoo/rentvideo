package com.example.rentvideo.controller;

import com.example.rentvideo.dto.AuthRequest;
import com.example.rentvideo.dto.RegisterRequest;
import com.example.rentvideo.service.AuthService;
import com.example.rentvideo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.ok("User registered successfully!");
    }

    @GetMapping("/login")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> login() {
//        authService.login(request);
        return ResponseEntity.ok("User Logged in successfully!");
    }
}