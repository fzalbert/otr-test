package org.example.dto.report;

import lombok.Getter;
import lombok.Setter;
import org.example.dto.appeal.ShortAppeal;

import java.util.Date;


@Getter
@Setter
public class Report {

    private Long id;

    private long employeeId;

    private String text;

    private ShortAppeal appeal;

    private ReportStatus reportStatus;

    private Date createDate;

    public Report() {
    }
}
