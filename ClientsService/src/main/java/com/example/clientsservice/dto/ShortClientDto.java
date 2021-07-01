package com.example.clientsservice.dto;

import com.example.clientsservice.domain.Client;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShortClientDto {

    private Long id;

    private String fullName;

    private String shortName;

    private String fullNameOrg;

    private String shortNameOrg;

    public ShortClientDto(Client client) {
        BeanUtils.copyProperties(client, this);
    }
}
