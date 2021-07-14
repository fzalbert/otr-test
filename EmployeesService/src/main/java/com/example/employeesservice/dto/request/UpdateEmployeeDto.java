package com.example.employeesservice.dto.request;

import com.example.employeesservice.domain.enums.RoleType;
import lombok.Data;
import org.springframework.lang.Nullable;


@Data
public class UpdateEmployeeDto {

    @Nullable
    private String login;

    @Nullable
    private String firstName;

    @Nullable
    private String password;

    @Nullable
    private String lastName;

    @Nullable
    private String email;

    @Nullable
    private RoleType roleType;
}
