package com.example.appealsservice.controller;

import com.example.appealsservice.httpModel.UserModel;

import javax.servlet.http.HttpServletRequest;

public abstract class AuthorizeController {

    protected UserModel userModel;

    public AuthorizeController(HttpServletRequest request) {

        var email = request.getHeader("email");
        var name = request.getHeader("name");
        var type = request.getHeader("role");

        userModel = new UserModel(
                Long.parseLong(request.getHeader("id")),
                email,name, type);

    }

}

