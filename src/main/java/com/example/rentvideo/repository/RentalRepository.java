package com.example.rentvideo.repository;

import com.example.rentvideo.entity.Rental;
import com.example.rentvideo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RentalRepository extends JpaRepository<Rental, Long> {
    List<Rental> findByUserAndReturnDateIsNull(User user);
    boolean existsByVideoIdAndReturnDateIsNull(Long videoId);
}