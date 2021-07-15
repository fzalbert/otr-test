package com.example.clientsservice.controller;

import com.example.clientsservice.dto.request.AuthDto;
import com.example.clientsservice.dto.request.CreateClientDto;
import com.example.clientsservice.dto.responce.ClientModelDto;
import com.example.clientsservice.service.ClientService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Log4j
@Scope("prototype")
@RestController
@RequestMapping("account")
public class AccountController {

    private final ClientService clientService;

    @Autowired
    public AccountController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("register")
    public void register(@RequestBody @Valid CreateClientDto request){

        log.debug("Request method: account/register user: " + request.getLogin());
        this.clientService.register((request));
    }

    @PostMapping("auth")
    public ClientModelDto auth(@RequestBody @Valid AuthDto request) {
        log.debug("Request method: account/auth user with login: " + request.getLogin());
        return this.clientService.auth(request);
    }
}
