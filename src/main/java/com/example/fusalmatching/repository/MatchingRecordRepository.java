package com.example.fusalmatching.repository;

import com.example.fusalmatching.domain.Field;
import com.example.fusalmatching.domain.MatchingRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MatchingRecordRepository extends JpaRepository<MatchingRecord, Long> {
    Optional<MatchingRecord> findByField_Id(Long id);
}
