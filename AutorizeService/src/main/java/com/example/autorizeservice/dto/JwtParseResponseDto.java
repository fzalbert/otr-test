package com.example.autorizeservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtParseResponseDto {

    private Long userId;

    private List<String> authorities;
}
