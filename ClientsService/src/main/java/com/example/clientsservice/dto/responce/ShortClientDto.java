package com.example.clientsservice.dto.responce;

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
    private String fio;
    private String fullNameOrg;
    private String shortNameOrg;

    public ShortClientDto(Client client) {
        BeanUtils.copyProperties(client, this);
    }
}
