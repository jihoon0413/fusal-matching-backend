package com.example.fusalmatching.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchingCancelRequestDto {
    private String teamId;
    private Long matchingId;

}
