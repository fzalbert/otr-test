package com.example.employeesservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthDto {
    @NotNull
    @Size(min = 5, max = 20, message = "Error login")
    private String login;

    @NotNull
    @Size(min = 5, message = "Error password")
    private String password;
}