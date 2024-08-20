package com.example.fusalmatching.dto.response;

import com.example.fusalmatching.domain.Team;
import com.example.fusalmatching.dto.TeamDto;

import java.util.List;

public record MyPageDto(
        TeamDto teamDto,
        List<MatchingRecordDto>matchingRecordList
) {

    public static MyPageDto of(TeamDto team, List<MatchingRecordDto> matchingRecordList) {
        return new MyPageDto(team,matchingRecordList);
    }


    public static MyPageDto from(Team team, List<MatchingRecordDto> matchingRecordList) {
        return MyPageDto.of(TeamDto.from(team), matchingRecordList);
    }


}
