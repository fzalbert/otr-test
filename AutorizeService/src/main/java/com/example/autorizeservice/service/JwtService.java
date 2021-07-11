package com.example.autorizeservice.service;

import com.example.autorizeservice.dto.JwtParseResponseDto;

public interface JwtService {

    JwtParseResponseDto parseJwt(String token);
    boolean isValidToken(String token);
}
