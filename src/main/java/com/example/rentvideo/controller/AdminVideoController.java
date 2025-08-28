package com.example.rentvideo.controller;

import com.example.rentvideo.dto.VideoDTO;
import com.example.rentvideo.dto.VideoResponse;
import com.example.rentvideo.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/videos/admin")
@RequiredArgsConstructor
public class AdminVideoController {
    @Autowired
    VideoService videoService;

    @PostMapping
    public ResponseEntity<VideoResponse> createVideo(@RequestBody VideoDTO videoDTO) {
        VideoResponse createdVideo = videoService.createVideo(videoDTO);
        return new ResponseEntity<>(createdVideo, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VideoResponse> updateVideo(@PathVariable Long id, @RequestBody VideoDTO videoDTO) {
        VideoResponse updatedVideo = videoService.updateVideo(id, videoDTO);
        return ResponseEntity.ok(updatedVideo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVideo(@PathVariable Long id) {
        videoService.deleteVideo(id);
        return ResponseEntity.noContent().build();
    }
}