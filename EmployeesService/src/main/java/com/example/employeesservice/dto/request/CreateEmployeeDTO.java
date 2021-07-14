package com.example.employeesservice.dto.request;

import com.example.employeesservice.domain.enums.RoleType;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CreateEmployeeDTO {

    @NotNull
    @Size(min = 5, max = 20, message = "Error login")
    private String login;

    @NotNull
    @Size(min = 5, message = "Error password")
    private String password;

    private String firstName;

    private String lastName;

    @NotNull
    @Email(message = "Error email")
    private String email;

    @NotNull
    private RoleType roleType;
}
