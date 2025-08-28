package com.example.rentvideo.repository;

import com.example.rentvideo.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VideoRepository extends JpaRepository<Video, Long> {
    List<Video> findByAvailable(boolean available);
}