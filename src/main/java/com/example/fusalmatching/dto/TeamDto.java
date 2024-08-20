package com.example.fusalmatching.dto;

import com.example.fusalmatching.domain.Team;

public record TeamDto(
        String id,
        String password,
        String teamName,
        String captainName,
        String tel,
        String email,
        String imgUrl,
        int manner,
        int skill
) {

    public static TeamDto of(String id, String password, String teamName, String captainName, String tel, String email, String imgUrl, int manner, int skill) {
        return new TeamDto(id, password,teamName,captainName,tel,email,imgUrl,manner,skill);
    }

    public static TeamDto from(Team entity) {
        return new TeamDto(
                entity.getId(),
                entity.getPassword(),
                entity.getTeamName(),
                entity.getCaptainName(),
                entity.getTel(),
                entity.getEmail(),
                entity.getImgUrl(),
                entity.getManner(),
                entity.getSkill()
        );
    }

    public Team toEntity() {
        return Team.of(id,teamName,password,captainName,tel,email);
    }

}
