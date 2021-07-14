package com.example.appealsservice.dto.request;

import org.springframework.lang.Nullable;

import java.util.Date;


public class AppealRequestDto {

    public Long themeId;

    @Nullable
    public Date endDate;

    @Nullable
    public Long tnvedId;

    @Nullable
    public Long catCostId;

    @Nullable
    public Double amount;

    @Nullable
    public String description;
    
}
