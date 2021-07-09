package com.example.appealsservice.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
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
    private String nameClient;

    @Column(name = "updateDate")
    private Date updateDate;

    @Enumerated(EnumType.ORDINAL)
    private StatusAppeal statusAppeal;

    @Column(name = "startDate")
    private Date startDate;

    @Column(name = "endDate")
    private Date endDate;

    @Nullable
    @Column(name = "tradeCode")
    private String tradeCode;

    @Nullable
    @Column(name = "amount")
    private double amount;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "theme_id", referencedColumnName = "id")
    private Theme theme;
}
