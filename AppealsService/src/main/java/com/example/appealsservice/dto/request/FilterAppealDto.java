package com.example.appealsservice.dto.request;

import com.example.appealsservice.domain.StatusAppeal;
import com.sun.istack.NotNull;

import java.util.Date;

public class FilterAppealDto {

    @NotNull
    public Long themeId;
    public Date date;

    public StatusAppeal statusAppeal;
}
