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

    private ThemeDto theme;

    private String nameOrg;

    private String description;

    private CostCatDto costCat;

    private Date endDate;

    private TNVEDDto tnved;

    private double amount;

    private Date createDate;

    private Integer statusAppeal;

    private List<FileDto> files;

    private ReportDto report;

    private TaskDto lastTask;

    public AppealDto()
        {}

    public AppealDto(Appeal appeal, List<FileDto> files, ReportDto report, TaskDto lastTask) {

        if(appeal == null)
            return;
        id = appeal.getId();
        clientId = appeal.getClientId();
        nameOrg = appeal.getNameOrg();
        theme = new ThemeDto(appeal.getTheme());
        costCat = new CostCatDto(appeal.getCostCat());
        description = appeal.getDescription();
        createDate = appeal.getCreateDate();
        amount = appeal.getAmount();
        statusAppeal = appeal.getStatusAppeal().getValue();
        endDate = appeal.getEndDate();
        tnved = new TNVEDDto(appeal.getTnved());
        this.files = files;
        this.report = report;
        this.lastTask = lastTask;

    }
}
