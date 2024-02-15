package com.example.fusalmatching.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FieldResponseDto {

    private Long id;
    private Long stadiumId;
    private String stadiumName;
    private String matchingDate;
    private String startTime;
    private String endTime;
    private Long matchingId;
    private int fieldNum;
    private boolean confirm;
    private boolean allRental;
    private List<TeamDto> team;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TeamDto {
        private String id;
        private String teamName;
        private String imageUrl;
        private int manner;
        private int skill;
        private Long teamMatchingId;
        private boolean evalOpposite;
        private boolean evalStadium;


    }


}
