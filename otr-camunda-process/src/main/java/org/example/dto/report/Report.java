package org.example.dto.report;

import lombok.Getter;
import lombok.Setter;
import org.example.dto.appeal.Appeal;

import java.util.Date;


@Getter
@Setter
public class Report {

    private Long id;

    private long employeeId;

    private String text;

    private Long appealId;

    private ReportStatus reportStatus;

    private Date createDate;

    public Report() {
    }
}
