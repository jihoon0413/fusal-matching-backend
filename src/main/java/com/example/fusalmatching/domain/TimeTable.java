package com.example.fusalmatching.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Array;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Entity
public class TimeTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter private ArrayList<Time> startTime;
    @Setter private ArrayList<Time> endTime;

    @OneToOne
    private Stadium stadium;


}
