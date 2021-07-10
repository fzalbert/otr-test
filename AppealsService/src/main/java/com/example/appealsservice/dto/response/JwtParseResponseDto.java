package com.example.appealsservice.dto.response;

import com.example.appealsservice.dto.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class JwtParseResponseDto {
    private Long userId;
    private UserType userType;
}