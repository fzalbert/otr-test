package com.example.appealsservice.controller;

import com.example.appealsservice.dto.request.JwtParseRequestDto;
import com.example.appealsservice.dto.response.JwtParseResponseDto;
import com.example.appealsservice.httpModel.UserModel;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Log4j
public abstract class AuthorizeController {

    protected UserModel userModel;
    private final String jwtParseUrl;

    public AuthorizeController(HttpServletRequest request, String jwtParseUrl) {
        this.jwtParseUrl = jwtParseUrl;
        log.debug("Request header 'id' = " + request.getHeader("id"));
        log.debug("Request header 'id' = " + request.getHeader("email"));
        log.debug("Request header 'id' = " + request.getHeader("role"));
        log.debug("Request header 'id' = " + URLDecoder.decode(request.getHeader("name"), StandardCharsets.UTF_8));
        log.debug("Request header 'authorize' = " + request.getHeader("Authorization"));

        String token = request.getHeader("Authorization");
        var model = parseJwt(token);

        var id = Long.parseLong(model.getAuthorities().get(0));
        var email = model.getAuthorities().get(2);
        var name = URLEncoder.encode(model.getAuthorities().get(1), StandardCharsets.UTF_8);
        var type = model.getAuthorities().get(3);

        userModel = new UserModel(id, email, name, type);
    }

    private JwtParseResponseDto parseJwt(String token) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(jwtParseUrl, new JwtParseRequestDto(token),
                JwtParseResponseDto.class);
    }

}

