package com.example.appealsservice.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "reports")
@Data
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_id")
    private long employeeId;

    @Column(name = "text", columnDefinition="TEXT")
    private String text;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "appeal_id", referencedColumnName = "id")
    private Appeal appeal;

    @Column(name = "reportStatus")
    private ReportStatus reportStatus;

    @Column(name = "createDate")
    private Date createDate;
}
