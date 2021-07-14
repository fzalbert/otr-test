package com.example.clientsservice.dto.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class CreateClientDto {
    @NotNull
    @Size(min = 5, max = 20, message = "Длина логина минимум 5 символов, максимум 20")
    private String login;

    @NotNull
    @Size(min = 5, message = "Длина пароля минимум 5 символов")
    private String password;

    @NotNull
    @Size(min = 5, message = "Минимальная длина ФИО 5 символов")
    private String fio;

    @NotNull
    @Pattern(regexp = "^(\\d{10}|\\d{12})$", message = "ИНН должен содержать 10 либо 12 символов")
    private String inn;


    @Pattern(regexp = "^(\\d{9})$", message = "КПП должен содержать 9 символов")
    private String kpp;

    @NotNull
    @Size(min = 5, message = "Полный адрес организации должен содержать минимум 5 символов")
    private String fullAddress;

    @NotNull
    @Size(min= 5, message = "Полное наименование организации должно содержать минимум 5 символов")
    private String fullNameOrg;

    private String shortNameOrg;

    @NotNull
    @Email(message = "Email должен быть корректным адресом электронной почты")
    private String email;


}
