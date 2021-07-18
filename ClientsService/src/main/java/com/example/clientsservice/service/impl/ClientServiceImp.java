package com.example.clientsservice.service.impl;

import com.example.clientsservice.domain.Client;
import com.example.clientsservice.domain.User;
import com.example.clientsservice.dto.request.AuthDto;
import com.example.clientsservice.dto.request.ClientDto;
import com.example.clientsservice.dto.request.CreateClientDto;
import com.example.clientsservice.dto.responce.ClientModelDto;
import com.example.clientsservice.dto.responce.ShortClientDto;
import com.example.clientsservice.exception.TemplateException;
import com.example.clientsservice.kafka.MessageSender;
import com.example.clientsservice.kafka.ModelMessage;
import com.example.clientsservice.repository.ClientRepository;
import com.example.clientsservice.repository.UserRepository;
import com.example.clientsservice.service.ClientService;
import com.example.clientsservice.util.CryptoHelper;
import com.example.clientsservice.validation.AuthDtoValidator;
import com.example.clientsservice.validation.ClientDtoValidator;
import com.example.clientsservice.validation.CreateClientDtoValidator;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Value;
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
    private final MessageSender messageSender;

    @Value("${secret}")
    private String secretKey;

    public ClientServiceImp(ClientRepository clientRepository, UserRepository userRepository,
                            ClientDtoValidator dtoValidator, CreateClientDtoValidator createClientDtoValidator,
                            AuthDtoValidator authDtoValidator, MessageSender messageSender) {
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
        this.dtoValidator = dtoValidator;
        this.createClientDtoValidator = createClientDtoValidator;
        this.authDtoValidator = authDtoValidator;
        this.messageSender = messageSender;
    }

    /**
     * Получение списка клиентов
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

        Client client = clientRepository.findById(id).orElseThrow(() -> new TemplateException("Клиент с таким id не найден"));

        return new ClientDto(client);
    }

    /**
     * Заблокировать клиента по Id
     */
    @Override
    public void blockById(long id) { this.changeActiveClient(false, id);}

    /**
     * Разблокировать клиента по id
     */
    @Override
    public void unblockById(long id) { this.changeActiveClient(true, id);}

    /**
     * Обновить клиента
     */
    @NotNull
    @Override
    public ClientDto update(ClientDto clientRequest) {
        dtoValidator.validate(clientRequest);
        var client = clientRepository.findById(clientRequest.getId()).orElseThrow(() -> new TemplateException("Клиент с таким id не найден"));

        client.getUser().setLogin(clientRequest.getLogin());

        Client updateClient = new Client(clientRequest);
        updateClient.setUser(client.getUser());
        clientRepository.save(updateClient);
        ClientDto newDto = new ClientDto(updateClient);

        return newDto;
    }

    /**
     * Удалить клиента
     */
    @Override
    public boolean deleteById(long id) {
        var client = clientRepository.findById(id).orElseThrow(() -> new TemplateException("Клиент с таким id не найден"));
        clientRepository.delete(client);
        return true;
    }

    /**
     * Сменить пароль
     */
    @Override
    public boolean changePassword(long id, String newPassword) {
        CryptoHelper.setSecretKey(secretKey);
        var client = clientRepository.findById(id).orElseThrow(() -> new TemplateException("Клиент с таким id не найден"));

        client.getUser().setPassword(CryptoHelper.HMAC(newPassword));
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
                .orElseThrow(() -> new TemplateException("Пользователь не найден"));

        var client = user.getClient();
        return new ClientModelDto(client.getId(), client.getEmail(), client.getFullNameOrg(), "CLIENT");
    }

    /**
     * Регистрация
     */
    @Override
    public void register(CreateClientDto request) {

        CryptoHelper.setSecretKey(secretKey);
        createClientDtoValidator.validate(request);
        var client = new Client(request);

        User user = new User();
        user.setActive(true);
        user.setLogin(request.getLogin());
        user.setAttemptsBlocking(0);
        user.setPassword(CryptoHelper.HMAC(request.getPassword()));
        user.setRegistrationDate(new Date());

        client.setUser(user);
        clientRepository.save(client);

        ModelMessage model = new ModelMessage();
        model.setEmail(client.getEmail());
        model.setName(client.getFio());
        model.setSubject("SUCCESSFUL REGISTRATION");
        model.setContent("Вы успешно зарегистрировались! ");
        messageSender.sendEmail(model);
    }

    /**
     * Изменить статус клиента
     */
    private void changeActiveClient(boolean isActive, long id) {

        var client = clientRepository
                .findById(id)
                .orElseThrow(() -> new TemplateException("Клиент с таким id не найден"));

        var user = userRepository
                .findById(client.getUser().getId())
                .orElseThrow(() -> new TemplateException("Пользователь не найден"));

        user.setActive(isActive);
        user.setAttemptsBlocking(0);
        userRepository.save(user);
    }


}
