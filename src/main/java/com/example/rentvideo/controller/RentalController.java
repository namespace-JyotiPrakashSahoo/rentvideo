package com.example.rentvideo.controller;

import com.example.rentvideo.service.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/rentals")
@RequiredArgsConstructor
public class RentalController {
    @Autowired
    RentalService rentalService;

    @PostMapping("/{videoId}/rent")
    public ResponseEntity<String> rentVideo(@PathVariable Long videoId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        rentalService.rentVideo(videoId, userEmail);
        return ResponseEntity.ok("Video rented successfully.");
    }

    @PostMapping("/{videoId}/return")
    public ResponseEntity<String> returnVideo(@PathVariable Long videoId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        rentalService.returnVideo(videoId, userEmail);
        return ResponseEntity.ok("Video returned successfully.");
    }
}