package com.example.fusalmatching.repository;

import com.example.fusalmatching.domain.Stadium;
import com.example.fusalmatching.domain.StadiumImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StadiumImageRepository extends JpaRepository<StadiumImage, Long> {
    List<StadiumImage> findAllByStadiumId(Long id);
}
