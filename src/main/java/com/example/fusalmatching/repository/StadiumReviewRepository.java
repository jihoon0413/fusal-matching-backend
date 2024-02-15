package com.example.fusalmatching.repository;

import com.example.fusalmatching.domain.Field;
import com.example.fusalmatching.domain.StadiumReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StadiumReviewRepository extends JpaRepository<StadiumReview, Long> {
    List<StadiumReview> findAllByStadiumId(Long id);
}
