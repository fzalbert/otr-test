package com.example.clientsservice.dto;

import com.example.clientsservice.domain.Client;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ClientDto {

    private Long id;

    private String fullName;

    private String shortName;

    private String inn;

    private String kpp;

    private String email;

    private String fullAddress;

    private String fullNameOrg;

    private String shortNameOrg;

    public ClientDto(Client client) {
        BeanUtils.copyProperties(client, this);
    }
}
