package com.example.fusalmatching.repository;

import com.example.fusalmatching.domain.MatchingRecord;
import com.example.fusalmatching.domain.TeamMatching;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeamMatchingRepository extends JpaRepository<TeamMatching, Long> {
    List<TeamMatching> findAllByMatchingRecord_Id(Long id);
    List<TeamMatching> findAllByTeam_Id(String id);
    void deleteByTeam_Id(String id);
}
