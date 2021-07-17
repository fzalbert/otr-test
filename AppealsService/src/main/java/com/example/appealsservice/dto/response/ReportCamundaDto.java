package com.example.appealsservice.dto.response;

import com.example.appealsservice.domain.Report;
import com.example.appealsservice.domain.enums.ReportStatus;
import lombok.Data;

import java.util.Date;

@Data
public class ReportCamundaDto {

    private Long id;

    private long employeeId;

    private String text;

    private Long appealId;

    private String reportStatus;

    private Date createDate;

    public ReportCamundaDto() {}

    public ReportCamundaDto (Report report) {

        if(report == null)
            return;

        id = report.getId();
        employeeId = report.getEmployeeId();
        text = report.getText();
        this.appealId = report.getAppeal().getId();
        reportStatus = report.getReportStatus().name();
        createDate = report.getCreateDate();
    }
}
