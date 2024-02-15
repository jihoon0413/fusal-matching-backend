package com.example.fusalmatching.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

@ToString
@Getter
@Entity
public class StadiumReview extends AuditingFields{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Stadium stadium;


    @Setter private int gpa;
    @Setter private String review;





    protected StadiumReview() {};

    private StadiumReview(Stadium stadium, int gpa, String review) {
        this.stadium = stadium;
        this.gpa = gpa;
        this.review = review;
    }

    public static StadiumReview of(Stadium stadium, int gpa, String review) {
        return new StadiumReview(stadium, gpa, review);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StadiumReview that = (StadiumReview) o;
        return gpa == that.gpa && Objects.equals(id, that.id) && Objects.equals(stadium, that.stadium) && Objects.equals(review, that.review);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, stadium, gpa, review);
    }
}
