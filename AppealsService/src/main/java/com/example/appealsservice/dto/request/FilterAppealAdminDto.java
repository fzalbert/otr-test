package com.example.appealsservice.dto.request;

import com.example.appealsservice.domain.enums.StatusAppeal;
import com.sun.istack.Nullable;

import java.util.Date;

public class FilterAppealAdminDto {
    @Nullable
    public Long themeId;

    @Nullable
    public Date date;

    @Nullable
    public StatusAppeal statusAppeal;

    @Nullable
    public Long employeeId;

    @Nullable
    public String nameOrg;
}
