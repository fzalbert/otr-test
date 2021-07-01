package com.example.clientsservice.service;

import com.example.clientsservice.dto.ClientDto;
import com.example.clientsservice.dto.ShortClientDto;

import java.util.List;

public interface ClientService {

    List<ShortClientDto> getAll();

    ClientDto getById(long id);

    void blockById(long id);

    void unblockById(long id);

    ClientDto update(ClientDto client);
    boolean deleteById(long id);
    boolean changePassword(String newPassword);

}
