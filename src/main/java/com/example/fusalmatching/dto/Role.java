package com.example.fusalmatching.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    ADMIN("ADMIN", "관리자"),
    MANAGER("MANAGER", "풋살업체"),
    USER("USER", "일반회원");

    private final String key;
    private final String title;
}
