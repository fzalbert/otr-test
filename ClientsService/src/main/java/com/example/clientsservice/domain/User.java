package com.example.clientsservice.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "userq")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "isActive")
    private boolean isActive;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "registrationDate")
    private Date registrationDate;

    @OneToOne(mappedBy = "user")
    private Client client;

}
