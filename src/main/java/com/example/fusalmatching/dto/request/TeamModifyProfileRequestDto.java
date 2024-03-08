package com.example.fusalmatching.dto.request;

import lombok.Getter;

@Getter
public class TeamModifyProfileRequestDto {

    private String id;
//    private String modifiedId;
    private String password;
    private String teamName;
    private String captainName;
    private String tel;
    private String email;
    private String imgUrl;
    private String modifiedInfo;
}
