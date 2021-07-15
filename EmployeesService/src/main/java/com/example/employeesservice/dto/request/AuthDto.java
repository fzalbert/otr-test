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
    @Size(min = 5, max = 20, message = "Длина логина минимум 5 символов, максимум 20")
    private String login;

    @NotNull
    @Size(min = 5, message = "Длина пароля минимум 5 символов")
    private String password;
}