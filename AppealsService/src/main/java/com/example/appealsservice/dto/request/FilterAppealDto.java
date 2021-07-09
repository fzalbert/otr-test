package com.example.appealsservice.dto.request;

import com.example.appealsservice.domain.StatusAppeal;
import com.sun.istack.Nullable;

import java.util.Date;

public class FilterAppealDto {

    @Nullable
    public Long themeId;

    @Nullable
    public Date date;

    @Nullable
    public StatusAppeal statusAppeal;
}
