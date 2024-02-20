package com.example.fusalmatching.service;

import com.example.fusalmatching.domain.*;
import com.example.fusalmatching.dto.request.StadiumReviewWriteRequestDto;
import com.example.fusalmatching.dto.request.TeamReviewWriteRequestDto;
import com.example.fusalmatching.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.Optional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final TeamMatchingRepository teamMatchingRepository;
    private final TeamRepository teamRepository;
    private final StadiumRepository stadiumRepository;
    private final TeamReviewRepository teamReviewRepository;
    private final StadiumReviewRepository stadiumReviewRepository;

    @Transactional
    public void writeTeamReview(TeamReviewWriteRequestDto teamReviewWriteRequestDto) {

        Optional<TeamMatching> teamMatching1 = teamMatchingRepository.findById(teamReviewWriteRequestDto.getTeamMatchingId());
        TeamMatching teamMatching = teamMatching1.get();
        if(!teamMatching.isEvalOpposite()) {
            teamMatching.setEvalOpposite(true);
            teamMatchingRepository.save(teamMatching);

            Optional<Team> team = teamRepository.findById(teamReviewWriteRequestDto.getOppositeTeamId());

            TeamReview teamReview = TeamReview.of(team.get(), teamReviewWriteRequestDto.getManner(), teamReviewWriteRequestDto.getSkill());
            teamReview.setCreatedBy(teamMatching.getTeam().getId());

            teamReviewRepository.save(teamReview);
        }
    }

    @Transactional
    public void writeStadiumReview(StadiumReviewWriteRequestDto stadiumReviewWriteRequestDto) {

        Optional<TeamMatching> teamMatching1 = teamMatchingRepository.findById(stadiumReviewWriteRequestDto.getTeamMatchingId());
        TeamMatching teamMatching = teamMatching1.get();
        if(!teamMatching.isEvalStadium()) {
            teamMatching.setEvalStadium(true);
            teamMatchingRepository.save(teamMatching);


            Optional<Stadium> stadium = stadiumRepository.findById(teamMatching.getMatchingRecord().getStadium().getId());

            StadiumReview stadiumReview = StadiumReview.of(stadium.get(), stadiumReviewWriteRequestDto.getGpa(), stadiumReviewWriteRequestDto.getReview());
            stadiumReview.setCreatedBy(teamMatching.getTeam().getId());

            stadiumReviewRepository.save(stadiumReview);
        }
    }


}
