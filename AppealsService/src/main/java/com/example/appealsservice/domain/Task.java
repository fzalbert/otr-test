package com.example.appealsservice.domain;

import com.example.appealsservice.domain.enums.TaskStatus;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "tasks")
@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employeeId")
    private Long employeeId;

    @Column(name = "taskStatus")
    @Enumerated(EnumType.ORDINAL)
    private TaskStatus taskStatus;

    @ManyToOne()
    @JoinColumn(name = "appealId", referencedColumnName = "id")
    private Appeal appeal;

    @Column(name = "isOver")
    private boolean isOver;

    @Column(name = "date")
    private Date date;
}
