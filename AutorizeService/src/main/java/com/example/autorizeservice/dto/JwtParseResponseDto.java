package com.example.autorizeservice.dto;

import java.util.List;

public class JwtParseResponseDto {

    private String username;
    private String name;
    private Long roleId;

    public JwtParseResponseDto() {
    }

    public JwtParseResponseDto(String username, Long roleId) {
        this.username = username;
        this.roleId = roleId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
