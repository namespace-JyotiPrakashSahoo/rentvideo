package com.example.rentvideo.service;

import com.example.rentvideo.entity.Rental;
import com.example.rentvideo.entity.User;
import com.example.rentvideo.entity.Video;
import com.example.rentvideo.repository.RentalRepository;
import com.example.rentvideo.repository.UserRepository;
import com.example.rentvideo.repository.VideoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RentalService {

    @Autowired
    RentalRepository rentalRepository;

    @Autowired
    VideoRepository videoRepository;

    @Autowired
    UserRepository userRepository;

    private static final int RENTAL_LIMIT = 2;

    @Transactional
    public void rentVideo(Long videoId, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        List<Rental> activeRentals = rentalRepository.findByUserAndReturnDateIsNull(user);
        if (activeRentals.size() >= RENTAL_LIMIT) {
            throw new IllegalStateException("You have reached the maximum rental limit of " + RENTAL_LIMIT + " videos.");
        }

        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new EntityNotFoundException("Video not found"));

        if (!video.isAvailable()) {
            throw new IllegalStateException("Video is not available for rent.");
        }

        video.setAvailable(false);
        videoRepository.save(video);

        Rental rental = Rental.builder()
                .user(user)
                .video(video)
                .rentalDate(LocalDateTime.now())
                .build();

        rentalRepository.save(rental);
    }

    @Transactional
    public void returnVideo(Long videoId, String userEmail) {
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new EntityNotFoundException("Video not found"));

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Rental rental = rentalRepository.findByUserAndReturnDateIsNull(user).stream()
                .filter(r -> r.getVideo().getId().equals(videoId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("You have not rented this video."));

        video.setAvailable(true);
        videoRepository.save(video);
        rental.setReturnDate(LocalDateTime.now());
        rentalRepository.save(rental);
    }
}