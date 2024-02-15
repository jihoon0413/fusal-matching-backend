package com.example.fusalmatching.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamResponseDto {
    private String id;
    private String teamName;
    private String captainName;
    private String tel;
    private String imageUrl;
    private int manner;
    private int skill;
    private List<MatchingRecordListDto> matchingRecordList;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MatchingRecordListDto {
        private MatchingRecordDto matchingRecord;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MatchingRecordDto {

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
        private MyTeamDto myTeamDto;
        private OppositeTeamDto oppositeTeamDto;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MyTeamDto {
        private String id;
//        private String teamName;
//        private String imageUrl;
//        private int manner;
//        private int skill;
        private Long teamMatchingId;
        private boolean evalOpposite;
        private boolean evalStadium;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OppositeTeamDto {
        private String id;
        private String teamName;
        private String imageUrl;
        private int manner;
        private int skill;
//        private Long teamMatchingId;
//        private boolean evalOpposite;
//        private boolean evalStadium;
    }


}
