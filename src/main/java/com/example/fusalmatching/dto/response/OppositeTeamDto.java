package com.example.fusalmatching.dto.response;

public record OppositeTeamDto(
        String id,
        String teamName,
        String imageUrl,
        int manner,
        int skill
) {

    public static OppositeTeamDto of(String id, String teamName, String imageUrl, int manner, int skill) {
        return new OppositeTeamDto(id,teamName,imageUrl,manner,skill);
    }

    public static OppositeTeamDto from(String id, String teamName, String imageUrl , int manner, int skill) {
        return OppositeTeamDto.of(id, teamName, imageUrl, manner, skill);
    }




}
