package com.example.clientsservice.service;

public interface ClientService {
    List<ShortClientDto> getAll();
    ClientDto getById(long id);
    ClientDto blockById(long id);
    ClientDto unblockById(long id);
    ClientDto update(ClientDto client, long id);
    boolean deleteById(long id);
    boolean changePassword(String newPassword);

}
