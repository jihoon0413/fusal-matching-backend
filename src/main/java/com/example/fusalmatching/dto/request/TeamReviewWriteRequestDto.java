package com.example.fusalmatching.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class TeamReviewWriteRequestDto {
    private Long teamMatchingId;
    private String oppositeTeamId;
    private int manner;
    private int skill;
}
