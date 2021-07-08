package com.example.appealsservice.dto.request;

import com.sun.istack.NotNull;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import java.util.Date;
import java.util.List;

public class AppealRequestDto {

    @NotNull
    public Long themeId;

    @NotNull
    public Long clientId;

    @Column(name = "startDate")
    public Date startDate;

    @Column(name = "endDate")
    public Date endDate;

    @Nullable
    @Column(name = "tradeCode")
    public String tradeCode;

    @Nullable
    @Column(name = "amount")
    public double amount;

    @Email(message = "Email должен быть корректным адресом электронной почты")
    public String email;

    public String clientName;

    public String description;
    
}
