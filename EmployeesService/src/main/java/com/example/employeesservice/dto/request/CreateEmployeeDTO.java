package com.example.employeesservice.dto.request;

import com.example.employeesservice.domain.enums.RoleType;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CreateEmployeeDTO {

    @NotNull
    @Size(min = 5, max = 20, message = "Длина логина минимум 5 символов, максимум 20")
    private String login;

    @NotNull
    @Size(min = 5, message = "Длина пароля минимум 5 символов")
    private String password;

    @NotNull
    @Size(min = 5, message = "Длина имени минимум 5 символов")
    private String firstName;

    @NotNull
    @Size(min = 5, message = "Длина фамилии минимум 5 символов")
    private String lastName;

    @NotNull
    @Email(message = "Email должен быть корректным адресом электронной почты")
    private String email;

    @NotNull
    private RoleType roleType;
}
