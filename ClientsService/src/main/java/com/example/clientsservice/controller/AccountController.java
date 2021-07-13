package com.example.clientsservice.controller;

import com.example.clientsservice.dto.request.AuthDto;
import com.example.clientsservice.dto.request.CreateClientDto;
import com.example.clientsservice.dto.responce.ClientModelDto;
import com.example.clientsservice.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

        this.clientService.register((request));
    }

    @PostMapping("auth")
    public ClientModelDto auth(@RequestBody @Valid AuthDto request) {
        return this.clientService.auth(request);
    }
}
