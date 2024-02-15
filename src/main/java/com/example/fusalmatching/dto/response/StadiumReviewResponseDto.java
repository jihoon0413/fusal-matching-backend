package com.example.fusalmatching.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StadiumReviewResponseDto {
    private int gpa;
    private String review;
    private String writer;


}
