package com.example.appealsservice.dto.request;

import com.sun.istack.NotNull;

import javax.validation.constraints.Email;

public class AppealRequestDto {

    @NotNull
    public Long themeId;

    @Email(message = "Email должен быть корректным адресом электронной почты")
    public String email;

    public String clientName;

    public String description;
    
}
