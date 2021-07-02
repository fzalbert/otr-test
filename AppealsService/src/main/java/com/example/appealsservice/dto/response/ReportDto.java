package com.example.appealsservice.dto.response;

import com.example.appealsservice.domain.Appeal;
import com.example.appealsservice.domain.Report;
import com.example.appealsservice.domain.ReportStatus;

import javax.persistence.*;
import java.util.Date;

public class ReportDto {

    private Long id;

    private long employeeId;

    private String text;

    private Long appealId;

    private ReportStatus reportStatus;

    private Date createDate;

    public ReportDto() {}

    public ReportDto (Report report) {

        id = report.getId();
        employeeId = report.getEmployeeId();
        text = report.getText();
        appealId = report.getAppeal().getId();
        reportStatus = report.getReportStatus();
        createDate = report.getCreateDate();
    }
}