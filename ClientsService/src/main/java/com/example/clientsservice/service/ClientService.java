package com.example.clientsservice.service;

import com.example.clientsservice.dto.request.ClientDto;
import com.example.clientsservice.dto.request.CreateClientDto;
import com.example.clientsservice.dto.responce.ShortClientDto;

import java.util.List;

public interface ClientService {

    List<ShortClientDto> getAll();
    ClientDto getById(long id);
    void blockById(long id);
    void unblockById(long id);
    ClientDto update(ClientDto client);
    boolean deleteById(long id);
    boolean changePassword(long id, String newPassword);
    Boolean auth(String login, String password);
    Boolean register(CreateClientDto request);
}
