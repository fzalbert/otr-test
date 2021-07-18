package com.example.employeesservice.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Модель для создание прав
 */
@Data
public class CreateRightDto {

    @NotNull
    private String title;
}
