package com.example.appealsservice.dto.response;


import com.example.appealsservice.domain.Appeal;
import com.example.appealsservice.domain.enums.StatusAppeal;
import com.example.appealsservice.domain.Theme;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class AppealDto {

    private Long id;

    private Long clientId;

    private Theme theme;

    private String description;

    private Date startDate;

    private Date endDate;

    private TNVEDDto tnved;

    private double amount;

    private Date createDate;

    private StatusAppeal statusAppeal;


    private List<FileDto> files;

    public AppealDto()
        {}

    public AppealDto(Appeal appeal, List<FileDto> files) {

        if(appeal == null)
            return;
        id = appeal.getId();
        clientId = appeal.getClientId();
        theme = appeal.getTheme();
        description = appeal.getDescription();
        createDate = appeal.getCreateDate();
        statusAppeal = appeal.getStatusAppeal();
        startDate = appeal.getStartDate();
        endDate = appeal.getEndDate();
        tnved = new TNVEDDto(appeal.getTnved());
        this.files = files;

    }
}
