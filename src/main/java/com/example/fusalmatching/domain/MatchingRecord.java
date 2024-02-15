package com.example.fusalmatching.domain;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Set;

@ToString
@Getter
@Entity
public class MatchingRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToMany(mappedBy = "matchingRecord")
    @ToString.Exclude
    private Set<TeamMatching> teamMatching;

    @ManyToOne
    private Stadium stadium;

    @OneToOne
    private Field field;


    private LocalTime confirmDate;

    @Setter
    private boolean confirm;

    @Setter
    private boolean allRental;


    protected MatchingRecord() {}

    private MatchingRecord(Stadium stadium, Field field) {
        this.stadium = stadium;
        this.field =field;

    }

    public static MatchingRecord of(Stadium stadium, Field field) {
        return new MatchingRecord(stadium, field);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MatchingRecord that = (MatchingRecord) o;
        return confirm == that.confirm && allRental == that.allRental && Objects.equals(id, that.id) && Objects.equals(teamMatching, that.teamMatching) && Objects.equals(stadium, that.stadium) && Objects.equals(field, that.field) && Objects.equals(confirmDate, that.confirmDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, teamMatching, stadium, field, confirmDate, confirm, allRental);
    }
}
