package com.example.autorizeservice.dto;

import java.util.List;

public class JwtParseResponseDto {

    private Long userId;

    public JwtParseResponseDto() {
    }

    public JwtParseResponseDto(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
