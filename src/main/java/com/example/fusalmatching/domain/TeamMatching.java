package com.example.fusalmatching.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

@ToString
@Getter
@Entity
public class TeamMatching {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Team team;

    @ManyToOne
    private MatchingRecord matchingRecord;

    @Setter
    private boolean evalOpposite;

    @Setter
    private boolean evalStadium;

    protected TeamMatching(){}

    private TeamMatching(Team team, MatchingRecord matchingRecord) {
        this.team = team;
        this.matchingRecord = matchingRecord;
    }

    public static TeamMatching of(Team team, MatchingRecord matchingRecord) {
        return new TeamMatching(team, matchingRecord);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeamMatching that = (TeamMatching) o;
        return evalOpposite == that.evalOpposite && evalStadium == that.evalStadium && Objects.equals(id, that.id) && Objects.equals(team, that.team) && Objects.equals(matchingRecord, that.matchingRecord);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, team, matchingRecord, evalOpposite, evalStadium);
    }
}
