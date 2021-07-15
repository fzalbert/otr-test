package com.example.employeesservice.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateRightDto {

    @NotNull
    public String title;
}
