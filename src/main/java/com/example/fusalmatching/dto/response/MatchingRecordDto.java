package com.example.fusalmatching.dto.response;

import com.example.fusalmatching.domain.Field;
import com.example.fusalmatching.domain.Team;
import com.example.fusalmatching.domain.TeamMatching;
import com.example.fusalmatching.dto.MyTeamDto;

import java.util.List;
import java.util.Objects;

public record MatchingRecordDto(
        Long id,
        Long stadiumId,
        String stadiumName,
        String matchingDate,
        String startTime,
        String endTime,
        Long matchingId,
        int fieldNum,
        boolean confirm,
        boolean allRental,
        MyTeamDto myTeamDto,
        OppositeTeamDto oppositeTeamDto
) {

    public static MatchingRecordDto of(Long id, Long stadiumId, String stadiumName, String matchingDate, String startTime, String endTime, Long matchingId, int fieldNum, boolean confirm, boolean allRental, MyTeamDto myTeamDto, OppositeTeamDto oppositeTeamDto) {
        return new MatchingRecordDto(id,stadiumId,stadiumName,matchingDate,startTime,endTime,matchingId,fieldNum,confirm,allRental,myTeamDto,oppositeTeamDto);
    }

    public static MatchingRecordDto from(Field field, Long matchingId, String id, boolean confirm, boolean allRental, List<TeamMatching> teamMatching1) {

        boolean myTeamEval1 = false;
        boolean myTeamEval2 = false;
        String oppositeId = "";
        String oppositeTeamName = "";
        String oppositeImageUrl ="";
        int oppositeManner = 0;
        int oppositeSkill = 0;

        for (TeamMatching teams : teamMatching1) {
            if (Objects.equals(id, teams.getTeam().getId())) {
                myTeamEval1 = teams.isEvalOpposite();
                myTeamEval2 = teams.isEvalStadium();
                MyTeamDto.from(id, matchingId, teams.isEvalOpposite(), teams.isEvalStadium());
            } else {
                Team opposite = teams.getTeam();
                oppositeId = opposite.getId();
                oppositeTeamName = opposite.getTeamName();
                oppositeImageUrl = opposite.getImgUrl();
                oppositeManner = opposite.getManner();
                oppositeSkill = opposite.getSkill();
            }
        }

        return MatchingRecordDto.of(
                field.getId(),
                field.getStadium().getId(),
                field.getStadium().getStadiumName(),
                String.valueOf(field.getMatchingDate()),
                String.valueOf(field.getStartTime()),
                String.valueOf(field.getEndTime()),
                matchingId,
                field.getFieldNum(),
                confirm,
                allRental,
                MyTeamDto.from(id,matchingId,myTeamEval1,myTeamEval2),
                OppositeTeamDto.from(oppositeId, oppositeTeamName, oppositeImageUrl, oppositeManner, oppositeSkill)
        );

    }
}
