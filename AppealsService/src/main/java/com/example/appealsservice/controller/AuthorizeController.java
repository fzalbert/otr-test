package com.example.appealsservice.controller;

import com.example.appealsservice.dto.enums.UserType;
import com.example.appealsservice.dto.request.JwtParseRequestDto;
import com.example.appealsservice.dto.response.JwtParseResponseDto;
import com.example.appealsservice.exception.AuthorizeException;
import com.example.appealsservice.httpModel.ClientModel;
import com.example.appealsservice.httpModel.EmployeeModel;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public abstract class AuthorizeController {

    private final String HEADER = "Authorization";

    private final String HEADER_VALUE_PREFIX = "Bearer";
    private final RestTemplate restTemplate = new RestTemplate();
    private final String JWT_PARSE_URL = "http://localhost:8090/v1/jwt/parse";
    protected ClientModel clientModel;

    public AuthorizeController(HttpServletRequest request) {

        var email = request.getHeader("email");
        var name = request.getHeader("name");

        clientModel = new ClientModel(
                Long.parseLong(request.getHeader("id")),
                email,name);

    }

}

