package com.example.appealsservice.dto.response;

import com.example.appealsservice.domain.Report;
import com.example.appealsservice.domain.enums.ReportStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ReportDto {

    private Long id;

    private long employeeId;

    private String text;

    private Long appealId;

    private ReportStatus reportStatus;

    private Date createDate;

    public ReportDto() {}

    public ReportDto (Report report) {

        if(report == null)
            return;

        id = report.getId();
        employeeId = report.getEmployeeId();
        text = report.getText();
        this.appealId = report.getAppeal().getId();
        reportStatus = report.getReportStatus();
        createDate = report.getCreateDate();
    }
}
