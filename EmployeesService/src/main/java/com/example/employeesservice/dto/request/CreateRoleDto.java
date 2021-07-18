package com.example.employeesservice.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateRoleDto {

    @NotNull
    private String title;
}
