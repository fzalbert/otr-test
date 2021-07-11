package com.example.appealsservice.dto.request;

import org.springframework.lang.Nullable;

import java.util.Date;

public class UpdateAppealRequestDto {

    @Nullable
    public Long themeId;

    @Nullable
    public Date startDate;

    @Nullable
    public Date endDate;

    @Nullable
    public Long tnvedId;

    @Nullable
    public Double amount;

    @Nullable
    public String description;
}
