package com.example.fusalmatching.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ManagerResponseDto {

    private String id;
    private String tel;
    private List<Stadium> stadiums;
    private List<MatchingRecordDto> matchingRecords;


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Stadium {
        private Long id;
        private String stadiumName;
        private String address;
        private String tel;
        private int fieldCount;
        private boolean noRest;
        private boolean parking;
        private boolean shower;
        private int gpa;
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MatchingRecordDto {
        private Long id;
        private String stadiumName;
        private String matchingDate;
        private String startTime;
        private String endTime;
        private Long matchingId;
        private int fieldNum;
        private boolean confirm;
        private boolean allRental;
        private List<TeamDto> teams;

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class TeamDto {
            private Long id;
            private String teamName;
            private int manner;
            private int skill;
            }
    }
}


