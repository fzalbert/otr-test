package com.example.employeesservice.dto.response;

public class JwtParseRequestDTO {
    private String token;

    public JwtParseRequestDTO() {
    }

    public JwtParseRequestDTO(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "JwtParseRequestDto{" +
                "token='" + token + '\'' +
                '}';
    }
}
