package com.example.rentvideo.controller;

import com.example.rentvideo.dto.VideoDTO;
import com.example.rentvideo.dto.VideoResponse;
import com.example.rentvideo.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/videos")
@RequiredArgsConstructor
public class VideoController {

    @Autowired
    VideoService videoService;

    @GetMapping
    public ResponseEntity<List<VideoResponse>> getAvailableVideos() {
        List<VideoResponse> videos = videoService.getAllAvailableVideos();
        return ResponseEntity.ok(videos);
    }
}