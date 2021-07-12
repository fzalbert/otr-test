package com.example.appealsservice.controller;

import com.example.appealsservice.httpModel.UserModel;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

public abstract class AuthorizeController {

    private final String HEADER = "Authorization";

    private final String HEADER_VALUE_PREFIX = "Bearer";
    private final RestTemplate restTemplate = new RestTemplate();
    private final String JWT_PARSE_URL = "http://localhost:8090/v1/jwt/parse";
    protected UserModel userModel;

    public AuthorizeController(HttpServletRequest request) {

        var email = request.getHeader("email");
        var name = request.getHeader("name");
        var type = request.getHeader("userType");

        userModel = new UserModel(
                Long.parseLong(request.getHeader("id")),
                email,name, type);

    }

}

