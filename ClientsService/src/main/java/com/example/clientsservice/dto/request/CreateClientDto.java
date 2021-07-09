package com.example.clientsservice.dto.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class CreateClientDto {
    @NotNull
    @Size(min = 5, max = 20, message = "Error login")
    private String login;

    @NotNull
    @Size(min = 5, message = "Error password")
    private String password;

    @NotNull
    @Size(min = 5, message = "Error Fio")
    private String fio;

    @NotNull
    @Pattern(regexp = "^(\\d{10}|\\d{12})$", message = "Error inn")
    private String inn;


    @Pattern(regexp = "^(\\d{9})$", message = "Error kpp")
    private String kpp;

    @NotNull
    @Size(min = 5, message = "Error FullAddress")
    private String fullAddress;

    @NotNull
    @Size(min= 5, message = "FullNameOrg")
    private String fullNameOrg;

    private String shortNameOrg;

    @NotNull
    @Email(message = "Email должен быть корректным адресом электронной почты")
    private String email;


}
