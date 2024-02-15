package com.example.fusalmatching.dto.request;

import lombok.Data;
import lombok.Getter;

@Getter
public class MatchingCreateRequestDto {

    private String team;
    private Long stadium;
    private Long field;
    private boolean allRental;



}
