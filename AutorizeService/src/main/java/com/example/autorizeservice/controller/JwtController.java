package com.example.autorizeservice.controller;

import com.example.autorizeservice.dto.JwtParseRequestDto;
import com.example.autorizeservice.dto.JwtParseResponseDto;
import com.example.autorizeservice.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/jwt")
public class JwtController {

    private final JwtService jwtService;

    @Autowired
    public JwtController(JwtService jwtService) {
        this.jwtService = jwtService;
    }


    @RequestMapping(value = "/parse", method = RequestMethod.POST)
    public ResponseEntity<?> getSomeSensitiveData(@RequestBody JwtParseRequestDto requestDto) {
            JwtParseResponseDto jwtParseResponseDto = jwtService.parseJwt(requestDto.getToken());
            return new ResponseEntity<>(jwtParseResponseDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/is-valid", method = RequestMethod.GET)
    public boolean isValid(String token) {
        return jwtService.isValidToken(token);
    }
}
