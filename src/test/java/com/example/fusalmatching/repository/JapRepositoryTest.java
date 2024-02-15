package com.example.fusalmatching.repository;

import com.example.fusalmatching.domain.Field;
import com.example.fusalmatching.domain.Stadium;
import com.example.fusalmatching.domain.Team;
import com.example.fusalmatching.domain.TeamReview;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
public class JapRepositoryTest {

    private final TeamRepository teamRepository;
    private final TeamReviewRepository teamReviewRepository;
    private final StadiumRepository stadiumRepository;
    private final FieldRepository fieldRepository;

    public JapRepositoryTest(
            @Autowired TeamRepository teamRepository,
            @Autowired TeamReviewRepository teamReviewRepository,
            @Autowired StadiumRepository stadiumRepository,
            @Autowired FieldRepository fieldRepository
            ) {
        this.teamRepository = teamRepository;
        this.teamReviewRepository = teamReviewRepository;
        this.stadiumRepository = stadiumRepository;
        this.fieldRepository = fieldRepository;
    }

    @DisplayName("Team,TeamReview 테스트")
    @Test
    void gives_when_then() {

        teamRepository.save(Team.of("dove1987","dove cun", "1234"));
        List<Team> teamList = teamRepository.findAll();
        Team team = teamList.get(0);
        team.setManner(0);

        teamReviewRepository.save(TeamReview.of(team, 3, 5));


//        List<TeamReview> reviewList = teamReviewRepository.findAll();
        TeamReview review = teamReviewRepository.findByTeam(team);

        int updateManner = review.getManner();
        int getMannerScore = team.getManner();

        team.setManner(getMannerScore + updateManner);

        Team updateTeam = teamRepository.saveAndFlush(team);


        assertThat(updateTeam).hasFieldOrPropertyWithValue("manner", 3);







    }
    @Test
    void createFieldFromStadium() {

        Optional<Stadium> stadium1 = stadiumRepository.findById(7L);

        Stadium stadium = stadium1.get();
        int fieldCount1 = stadium.getFieldCount();
        String cost = stadium.getCost();

        SimpleDateFormat time1 = new SimpleDateFormat("HH:mm");
        SimpleDateFormat date1 = new SimpleDateFormat("yy-MM-dd");
        Time startTime = Time.valueOf(time1.format("18:00"));
        Time endTime = Time.valueOf(time1.format("20:00"));




        Field field = Field.of(stadium, java.sql.Date.valueOf("2022-04-09"), startTime, endTime, cost);

        fieldRepository.save(field);



    }


}
