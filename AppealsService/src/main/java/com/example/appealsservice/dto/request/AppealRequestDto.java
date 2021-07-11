package com.example.appealsservice.dto.request;

import com.sun.istack.NotNull;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Email;
import java.util.Date;


public class AppealRequestDto {

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
