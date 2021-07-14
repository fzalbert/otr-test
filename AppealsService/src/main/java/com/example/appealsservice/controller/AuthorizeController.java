package com.example.appealsservice.controller;

import com.example.appealsservice.httpModel.UserModel;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public abstract class AuthorizeController {

    protected UserModel userModel;

    public AuthorizeController(HttpServletRequest request) {

        var email = request.getHeader("email");
        var name = URLDecoder.decode(request.getHeader("name"), StandardCharsets.UTF_8);
        var type = request.getHeader("role");

        userModel = new UserModel(
                Long.parseLong(request.getHeader("id")),
                email,name, type);
    }

}

