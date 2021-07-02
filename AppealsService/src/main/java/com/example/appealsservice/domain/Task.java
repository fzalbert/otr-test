package com.example.appealsservice.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "tasks")
@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_id")
    private long employeeId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "appeal_id", referencedColumnName = "id")
    private Appeal appeal;

    @Column(name = "isOver")
    private boolean isOver;

    @Column(name = "date")
    private Date date;
}
