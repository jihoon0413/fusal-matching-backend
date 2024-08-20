package com.example.fusalmatching.dto;

import com.example.fusalmatching.dto.response.MyPageDto;

public record MyTeamDto(
        String id,
        Long teamMatchingId,
        boolean evalOpposite,
        boolean evalStadium
) {

    public static MyTeamDto of(String id, Long teamMatchingId, boolean evalOpposite, boolean evalStadium) {
        return new MyTeamDto(id, teamMatchingId, evalOpposite, evalStadium);
    }

    public static MyTeamDto from(String id, Long teamMatchingId, boolean evalOpposite, boolean evalStadium) {
        return MyTeamDto.of(
                id,
                teamMatchingId,
                evalOpposite,
                evalStadium
        );


    }


}
