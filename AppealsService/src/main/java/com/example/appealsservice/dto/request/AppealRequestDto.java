package com.example.appealsservice.dto.request;

import com.sun.istack.NotNull;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Email;
import java.util.Date;


public class AppealRequestDto {

    @NotNull
    public Long themeId;

    @NotNull
    public Long clientId;


    public Date startDate;

    public Date endDate;

    @Nullable
    public Long tnvedId;

    @Nullable
    public Double amount;

    @Email(message = "Email должен быть корректным адресом электронной почты")
    public String email;

    public String clientName;

    public String description;
    
}
