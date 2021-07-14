package com.example.appealsservice.domain;

import com.example.appealsservice.domain.enums.StatusAppeal;
import lombok.Data;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "appeals")
@Data
public class Appeal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "clientId")
    private Long clientId;


    @Column(name = "description", columnDefinition="TEXT")
    private String description;

    @Column(name = "createDate")
    private Date createDate;

    @Column(name = "email")
    private String email;

    @Column(name = "nameClient")
    private String nameOrg;

    @Column(name = "updateDate")
    private Date updateDate;

    @Enumerated(EnumType.ORDINAL)
    private StatusAppeal statusAppeal;

    @Column(name = "isOver")
    private boolean isOver;

    @Column(name = "endDate")
    private Date endDate;

    @Nullable
    @Column(name = "amount")
    private double amount;


    @ManyToOne()
    @JoinColumn(name = "costcatId", referencedColumnName = "id", nullable = true)
    private CostCat costCat;

    @ManyToOne()
    @JoinColumn(name = "tnvedId", referencedColumnName = "id", nullable = true)
    private TNVED tnved;

    @ManyToOne()
    @JoinColumn(name = "themeId", referencedColumnName = "id")
    private Theme theme;
}
