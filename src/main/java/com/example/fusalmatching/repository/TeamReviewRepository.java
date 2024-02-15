package com.example.fusalmatching.repository;

import com.example.fusalmatching.domain.Field;
import com.example.fusalmatching.domain.Team;
import com.example.fusalmatching.domain.TeamReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamReviewRepository extends JpaRepository<TeamReview, Long> {
    List<TeamReview> findAllByTeamId(String id);
//    TeamReview findByTeam(Team team);
}

