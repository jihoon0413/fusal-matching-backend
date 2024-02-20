package com.example.fusalmatching.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class StadiumReviewWriteRequestDto {
    private Long teamMatchingId;
    private int gpa;
    private String review;

}
