package com.example.rentvideo.controller;

import com.example.rentvideo.dto.VideoDTO;
import com.example.rentvideo.dto.VideoResponse;
import com.example.rentvideo.service.VideoService;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/videos")
@RequiredArgsConstructor
public class VideoController {
    private final VideoService videoService;

    @PermitAll
    @GetMapping
    public ResponseEntity<List<VideoResponse>> getAvailableVideos() {
        List<VideoResponse> videos = videoService.getAllAvailableVideos();
        return ResponseEntity.ok(videos);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<VideoResponse> createVideo(@RequestBody VideoDTO videoDTO) {
        VideoResponse createdVideo = videoService.createVideo(videoDTO);
        return new ResponseEntity<>(createdVideo, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<VideoResponse> updateVideo(@PathVariable Long id, @RequestBody VideoDTO videoDTO) {
        VideoResponse updatedVideo = videoService.updateVideo(id, videoDTO);
        return ResponseEntity.ok(updatedVideo);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteVideo(@PathVariable Long id) {
        videoService.deleteVideo(id);
        return ResponseEntity.noContent().build();
    }
}