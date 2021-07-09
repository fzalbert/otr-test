package com.example.employeesservice.dto.response;

public class JwtParseResponseDTO {
    private Long userId;

    public JwtParseResponseDTO() {
    }

    public JwtParseResponseDTO(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
