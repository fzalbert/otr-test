package com.example.clientsservice.dto.request;

import com.example.clientsservice.domain.Client;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto {

    @NotNull
    private Long id;

    @Size(min = 5, message = "Error Fio")
    private String fio;

    @Pattern(regexp = "^(\\d{10}|\\d{12})$", message = "Error inn")
    private String inn;

    @Pattern(regexp = "^(\\d{9})$", message = "Error kpp")
    private String kpp;

    @Email(message = "Email error")
    private String email;

    @Size(min = 5, message = "Error FullAddress")
    private String fullAddress;

    @Size(min= 5, message = "FullNameOrg")
    private String fullNameOrg;

    private String shortNameOrg;

    public ClientDto(Client client) {
        BeanUtils.copyProperties(client, this);
    }
}
