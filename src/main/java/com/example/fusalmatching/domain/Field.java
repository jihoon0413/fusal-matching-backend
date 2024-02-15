package com.example.fusalmatching.domain;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;


import java.util.Objects;

@ToString
@Getter
@Table(indexes = {
        @Index(columnList = "matchingDate"),
        @Index(columnList = "startTime")
})
@Entity
public class Field {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "stadium_id")
    private Stadium stadium;

    private Date matchingDate;
    private Time startTime;
    private Time endTime;
    private String cost;
    private int fieldNum;





    protected Field() {}

    private Field(Stadium stadium, Date matchingDate, Time startTime, Time endTime, String cost, int fieldNum) {
        this.stadium = stadium;
        this.matchingDate = matchingDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.cost = cost;
        this.fieldNum =fieldNum;
    }

    public static Field of(Stadium stadium, Date matchingDate, Time startTime, Time endTime, String cost, int fieldNum){
        return new Field(stadium, matchingDate, startTime, endTime, cost, fieldNum);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Field field = (Field) o;
        return fieldNum == field.fieldNum && Objects.equals(id, field.id) && Objects.equals(stadium, field.stadium) && Objects.equals(matchingDate, field.matchingDate) && Objects.equals(startTime, field.startTime) && Objects.equals(endTime, field.endTime) && Objects.equals(cost, field.cost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, stadium, matchingDate, startTime, endTime, cost, fieldNum);
    }
}
