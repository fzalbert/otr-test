package com.example.appealsservice.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "appeal")
@Data
public class Appeal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_id")
    private Long clientId;

    @Column(name = "description", columnDefinition="TEXT")
    private String description;

    @Column(name = "createDate")
    private Date createDate;

    @Column(name = "updateDate")
    private Date updateDate;

    @Enumerated(EnumType.ORDINAL)
    private StatusAppeal statusAppeal;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "theme_id", referencedColumnName = "id")
    private Theme theme;
}
