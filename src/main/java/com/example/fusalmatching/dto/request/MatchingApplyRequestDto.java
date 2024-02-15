package com.example.fusalmatching.dto.request;

import lombok.Getter;

@Getter
public class MatchingApplyRequestDto {

    private Long matchingId;
    private String team;
    private Long stadium;
    private Long field;
}
