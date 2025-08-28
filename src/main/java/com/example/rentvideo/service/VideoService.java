package com.example.rentvideo.service;

import com.example.rentvideo.dto.VideoDTO;
import com.example.rentvideo.dto.VideoResponse;
import com.example.rentvideo.entity.Video;
import com.example.rentvideo.repository.VideoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VideoService {
    private final VideoRepository videoRepository;

    public List<VideoResponse> getAllAvailableVideos() {
        return videoRepository.findByAvailable(true).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public VideoResponse createVideo(VideoDTO videoDTO) {
        Video video = Video.builder()
                .title(videoDTO.getTitle())
                .director(videoDTO.getDirector())
                .genre(videoDTO.getGenre())
                .available(true)
                .build();
        Video savedVideo = videoRepository.save(video);
        return mapToResponse(savedVideo);
    }

    public VideoResponse updateVideo(Long id, VideoDTO videoDTO) {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Video not found with id: " + id));

        video.setTitle(videoDTO.getTitle());
        video.setDirector(videoDTO.getDirector());
        video.setGenre(videoDTO.getGenre());
        Video updatedVideo = videoRepository.save(video);
        return mapToResponse(updatedVideo);
    }

    public void deleteVideo(Long id) {
        if (!videoRepository.existsById(id)) {
            throw new EntityNotFoundException("Video not found with id: " + id);
        }
        videoRepository.deleteById(id);
    }

    private VideoResponse mapToResponse(Video video) {
        return VideoResponse.builder()
                .id(video.getId())
                .title(video.getTitle())
                .director(video.getDirector())
                .genre(video.getGenre())
                .available(video.isAvailable())
                .build();
    }
}