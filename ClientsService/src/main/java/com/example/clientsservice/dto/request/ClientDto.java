package com.example.clientsservice.dto.request;

import com.example.clientsservice.domain.Client;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto {

    @NotNull
    private Long id;

    @Size(min = 5, message = "Error Full Name")
    private String fullName;

    private String shortName;

    @Size(min = 10, max = 12, message = "Error INN")
    private String inn;

    @Size(min = 9 ,max = 9, message = "Error kpp")
    private String kpp;

    @Email(message = "Email должен быть корректным адресом электронной почты")
    private String email;

    @Size(min = 5, message = "Error FullAdress")
    private String fullAddress;

    @Size(min= 5, message = "FullNameOrg")
    private String fullNameOrg;

    private String shortNameOrg;

    public ClientDto(Client client) {
        BeanUtils.copyProperties(client, this);
    }
}
