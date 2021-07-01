package com.example.clientsservice.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@Entity
@Table(name = "client")
@Data
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fullName")
    private String fullName;

    @Column(name = "inn")
    private String inn;

    @Column(name = "kpp")
    private String kpp;

    @Column(name = "shortName")
    private String shortName;

    @Column(name = "fullAddress")
    private String fullAddress;

    @Column(name = "fullNameOrg")
    private String fullNameOrg;

    @Column(name = "shortNameOrg")
    private String shortNameOrg;

    @Column(name = "email")
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private  User user;


}

