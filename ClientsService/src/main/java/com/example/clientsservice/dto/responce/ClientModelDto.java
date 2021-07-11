package com.example.clientsservice.dto.responce;

import com.example.clientsservice.domain.Client;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class ClientModelDto {
    private Long id;
    private String email;
    private String fullNameOrg;

    public ClientModelDto(Client client) {
        BeanUtils.copyProperties(client, this);
    }
}
