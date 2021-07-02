package com.example.appealsservice.dto.request;

import com.example.appealsservice.domain.StatusAppeal;

import java.util.Date;

public class FilterAppealDto {

    public Long themeId;
    public Date date;
    public StatusAppeal statusAppeal;
}
