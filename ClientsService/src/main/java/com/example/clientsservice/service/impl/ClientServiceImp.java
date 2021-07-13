package com.example.clientsservice.service.impl;

import com.example.clientsservice.domain.Client;
import com.example.clientsservice.domain.User;
import com.example.clientsservice.dto.request.AuthDto;
import com.example.clientsservice.dto.request.ClientDto;
import com.example.clientsservice.dto.request.CreateClientDto;
import com.example.clientsservice.dto.responce.ClientModelDto;
import com.example.clientsservice.dto.responce.ShortClientDto;
import com.example.clientsservice.exception.ResourceNotFoundException;
import com.example.clientsservice.repository.ClientRepository;
import com.example.clientsservice.repository.UserRepository;
import com.example.clientsservice.service.ClientService;
import com.example.clientsservice.validation.AuthDtoValidator;
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
public class ClientServiceImp implements ClientService {

    private final ClientRepository clientRepository;
    private final UserRepository userRepository;
    private final ClientDtoValidator dtoValidator;
    private final CreateClientDtoValidator createClientDtoValidator;
    private final AuthDtoValidator authDtoValidator;

    public ClientServiceImp(ClientRepository clientRepository, UserRepository userRepository,
                            ClientDtoValidator dtoValidator, CreateClientDtoValidator createClientDtoValidator,
                            AuthDtoValidator authDtoValidator) {
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
        this.dtoValidator = dtoValidator;
        this.createClientDtoValidator = createClientDtoValidator;
        this.authDtoValidator = authDtoValidator;
    }

    /**
     * Получение всех клиентов
     */
    @Override
    public List<ShortClientDto> getAll() {

        return clientRepository
                .findAll()
                .stream()
                .sorted(Comparator.comparing(Client::getFio, Comparator.reverseOrder()))
                .map(ShortClientDto::new)
                .collect(Collectors.toList());
    }

    /**
     * Получение клиента по id
     */
    @Override
    public ClientDto getById(long id) {

        Client client = clientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));

        return new ClientDto(client);
    }

    /**
     * Заблокировать клиента по Id
     */
    @Override
    public void blockById(long id) {

        var client = clientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));

        var user = userRepository
                .findById(client.getUser().getId())
                .get();

        user.setActive(false);

        userRepository.save(user);
    }

    /**
     * Разблокировать клиента по id
     */
    @Override
    public void unblockById(long id) {
        var client = clientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));

        var user = userRepository
                .findById(client.getUser().getId())
                .get();

        user.setActive(true);
        user.setAttemptsBlocking(0);

        userRepository.save(user);
    }

    /**
     * Обновить клиента
     */
    @NotNull
    @Override
    public ClientDto update(ClientDto clientRequest) {
        dtoValidator.validate(clientRequest);
        var client = clientRepository.findById(clientRequest.getId()).orElseThrow(() -> new ResourceNotFoundException(clientRequest.getId()));
        if (client == null)
            return null;

        Client updateClient = new Client(clientRequest);
        updateClient.setUser(client.getUser());
        clientRepository.save(updateClient);
        ClientDto newDto = new ClientDto(updateClient);

        return newDto;
    }

    /**
     * Удалить клиента по id
     */
    @Override
    public boolean deleteById(long id) {
        var client = clientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        clientRepository.delete(client);
        return true;
    }

    /**
     * Сменить пароль
     */
    @Override
    public boolean changePassword(long id, String newPassword) {
        var client = clientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));

        client.getUser().setPassword(newPassword);
        clientRepository.save(client);

        return true;
    }

    /**
     * Авторизация
     */
    @Override
    public ClientModelDto auth(AuthDto authRequest) {
        authDtoValidator.validate(authRequest);
        var user = userRepository
                .findByLogin(authRequest.getLogin())
                .orElse(null);

        var client = user.getClient();
        return new ClientModelDto(client.getId(), client.getEmail(), client.getFullNameOrg(), "CLIENT");
    }

    /**
     * Регистрация
     */
    @Override
    public void register(CreateClientDto request) {
        createClientDtoValidator.validate(request);
        var client = new Client(request);

        User user = new User();
        user.setActive(true);
        user.setLogin(request.getLogin());
        user.setPassword(DigestUtils.md5Hex(request.getPassword()));
        user.setRegistrationDate(new Date());

        client.setUser(user);
        clientRepository.save(client);
    }


}
