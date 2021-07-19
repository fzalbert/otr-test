package com.example.appealsservice.controller;

import com.example.appealsservice.httpModel.UserModel;
import lombok.extern.log4j.Log4j;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Log4j
public abstract class AuthorizeController {

    protected UserModel userModel;

    public AuthorizeController(HttpServletRequest request) {

        log.debug("Request header 'id' = " + request.getHeader("id"));
        log.debug("Request header 'id' = " + request.getHeader("email"));
        log.debug("Request header 'id' = " + request.getHeader("role"));
        log.debug("Request header 'id' = " + URLDecoder.decode(request.getHeader("name"), StandardCharsets.UTF_8));
        log.debug("Request header 'authorize' = " + request.getHeader("Authorization"));

        var email = request.getHeader("email");
        var name = URLDecoder.decode(request.getHeader("name"), StandardCharsets.UTF_8);
        var type = request.getHeader("role");

        userModel = new UserModel(
                Long.parseLong(request.getHeader("id")),
                email, name, type);
    }

}

