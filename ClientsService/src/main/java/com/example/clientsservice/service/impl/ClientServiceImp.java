package com.example.clientsservice.service.impl;

import com.example.clientsservice.domain.Client;
import com.example.clientsservice.domain.User;
import com.example.clientsservice.dto.request.ClientDto;
import com.example.clientsservice.dto.request.CreateClientDto;
import com.example.clientsservice.dto.responce.ShortClientDto;
import com.example.clientsservice.exception.ResourceNotFoundException;
import com.example.clientsservice.repository.ClientRepository;
import com.example.clientsservice.repository.UserRepository;
import com.example.clientsservice.service.ClientService;
import com.example.clientsservice.validation.ClientDtoValidator;
import com.example.clientsservice.validation.CreateClientDtoValidator;
import com.sun.istack.NotNull;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ClientServiceImp implements ClientService{

    private final ClientRepository clientRepository;
    private final UserRepository userRepository;
    private final ClientDtoValidator dtoValidator;
    private final CreateClientDtoValidator createClientDtoValidator;

    public ClientServiceImp(ClientRepository clientRepository, UserRepository userRepository,
                            ClientDtoValidator dtoValidator, CreateClientDtoValidator createClientDtoValidator)
    {
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
        this.dtoValidator = dtoValidator;
        this.createClientDtoValidator = createClientDtoValidator;
    }

    /**Получение всех клиентов*/
    @Override
    public List<ShortClientDto> getAll() {

        return clientRepository
                .findAll()
                .stream()
                .sorted(Comparator.comparing(Client::getShortName, Comparator.reverseOrder()))
                .map(ShortClientDto::new)
                .collect(Collectors.toList());
    }

    /**Получение клиента по id */
    @Override
    public ClientDto getById(long id) {

        Client client = clientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));

        return new ClientDto(client);
    }

    /**Заблокировать клиента по Id*/
    @Override
    public void blockById(long id) {

        var client = clientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));

        var user = userRepository
                .findAll()
                .stream()
                .filter(x -> x.getId() == client.getUser().getId())
                .findFirst()
                .get();

        user.setActive(false);

        userRepository.save(user);
    }

    /**Разблокировать клиента по id*/
    @Override
    public void unblockById(long id) {
        var client = clientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));

        var user = userRepository
                .findAll()
                .stream()
                .filter(x -> x.getId() == client.getUser().getId())
                .findFirst()
                .get();

        user.setActive(true);

        userRepository.save(user);
    }

    /**Обновить клиента*/
    @NotNull
    @Override
    public ClientDto update(ClientDto clientrequest) {
        dtoValidator.validate(clientrequest);
        var client = clientRepository.findById(clientrequest.getId()).orElseThrow(() -> new ResourceNotFoundException(clientrequest.getId()));
        if(client == null)
            return null;

        Client updateClient = new Client(clientrequest);
        updateClient.setUser(client.getUser());
        clientRepository.save(updateClient);
        ClientDto newDto = new ClientDto(updateClient);

        return  newDto;
    }

    /**Удалить клиента по id*/
    @Override
    public boolean deleteById(long id) {
        var client = clientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
                if(client == null);

                clientRepository.delete(client);

                return true;

    }

    /**Сменить пароль*/
    @Override
    public boolean changePassword(long id, String newPassword) {
        var client = clientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));

        client.getUser().setPassword(newPassword);
        clientRepository.save(client);

        return true;
    }

    @Override
    public Boolean auth(String login, String password) {

        var md5Password = DigestUtils.md5Hex(password);

        var user = userRepository
                .findAll()
                .stream()
                .filter(x -> x.getLogin().equals(login) && md5Password.equals(x.getPassword()))
                .findFirst()
                .orElse(null);

        return user != null;
    }

    @Override
    public Boolean register(CreateClientDto request) {
        createClientDtoValidator.validate(request);
        var client = new Client(request);

        User user = new User();
        user.setActive(true);
        user.setLogin(request.getLogin());
        user.setPassword(DigestUtils.md5Hex(request.getPassword()));
        user.setRegistrationDate(new Date());

        client.setUser(user);
        clientRepository.save(client);
        return true;
    }


}
