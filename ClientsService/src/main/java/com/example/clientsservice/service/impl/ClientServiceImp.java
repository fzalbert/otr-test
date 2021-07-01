package com.example.clientsservice.service.impl;

import com.example.clientsservice.domain.Client;
import com.example.clientsservice.dto.ClientDto;
import com.example.clientsservice.dto.ShortClientDto;
import com.example.clientsservice.repository.ClientRepository;
import com.example.clientsservice.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ClientServiceImp implements ClientService{

    private ClientRepository clientRepository;

    public ClientServiceImp(ClientRepository clientRepository)
    {
        this.clientRepository = clientRepository;
    }

    @Override
    public List<ShortClientDto> getAll() {

        return clientRepository
                .findAll()
                .stream()
                .sorted(Comparator.comparing(Client::getShortName, Comparator.reverseOrder()))
                .map(x -> new ShortClientDto(x))
                .collect(Collectors.toList());
    }

    @Override
    public ClientDto getById(long id) {

        Client client = clientRepository.getById(id);

        return new ClientDto(client);
    }

    @Override
    public void blockById(long id) {

        var client = clientRepository.getById(id);
        if(client == null);



    }

    @Override
    public void unblockById(long id) {

    }

    @Override
    public ClientDto update(ClientDto clientrequest) {

        var client = clientRepository.getById(clientrequest.getId());
        if(client == null)
            return null;
        clientRepository.save(client);

        return  null;
    }

    @Override
    public boolean deleteById(long id) {
        var client = clientRepository.getById(id);
                if(client == null);

                clientRepository.delete(client);

                return true;

    }

    @Override
    public boolean changePassword(String newPassword) {

        return false;
    }
}
