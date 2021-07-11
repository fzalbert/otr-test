package com.example.clientsservice.domain;

import com.example.clientsservice.dto.request.ClientDto;
import com.example.clientsservice.dto.request.CreateClientDto;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;

@Entity
@Table(name = "client")
@Data
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fio")
    private String fio;

    @Column(name = "inn")
    private String inn;

    @Column(name = "kpp")
    private String kpp;

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
    private User user;

    public Client(ClientDto client) { BeanUtils.copyProperties(client, this);
    }
    public Client(CreateClientDto client) { BeanUtils.copyProperties(client, this);
    }

    public Client() {}
}

