package com.example.employeesservice.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Модель для создание роли
 */
@Data
public class CreateRoleDto {

    @NotNull
    private String title;
}
