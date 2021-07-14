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

    @Size(min = 5, message = "Минимальная длина ФИО 5 символов")
    private String fio;

    @Pattern(regexp = "^(\\d{10}|\\d{12})$", message = "ИНН должен содержать 10 либо 12 символов")
    private String inn;

    @Pattern(regexp = "^(\\d{9})$", message = "КПП должен содержать 9 символов")
    private String kpp;

    @Email(message = "Email должен быть корректным адресом электронной почты")
    private String email;

    @Size(min = 5, message = "Полный адрес организации должен содержать минимум 5 символов")
    private String fullAddress;

    @Size(min= 5, message = "Полное наименование организации должно содержать минимум 5 символов ")
    private String fullNameOrg;

    private String shortNameOrg;

    private String login;

    public ClientDto(Client client) {
        BeanUtils.copyProperties(client, this);
        this.login = client.getUser().getLogin();
    }
}
