package com.example.fusalmatching.repository;

import com.example.fusalmatching.domain.Field;
import com.example.fusalmatching.domain.Stadium;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface StadiumRepository extends JpaRepository<Stadium, Long> {
    List<Stadium> findAllByManagerId(String id);
}
