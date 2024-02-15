package com.example.fusalmatching.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

@ToString
@Getter
@Entity
public class TeamReview extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    private Team team;


    @Setter
    @Column(nullable = false)
    private int manner;

    @Setter
    @Column(nullable = false)
    private int skill;





    protected TeamReview() {};

    private TeamReview(Team team, int manner, int skill) {
        this.team = team;
        this.manner =manner;
        this.skill = skill;
    }

    public static TeamReview of(Team team, int manner, int skill) {
        return new TeamReview(team, manner, skill);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeamReview that = (TeamReview) o;
        return manner == that.manner && skill == that.skill && Objects.equals(id, that.id) && Objects.equals(team, that.team);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, team, manner, skill);
    }
}
