package com.example.clientsservice.controller;

import com.example.clientsservice.dto.ShortClientDto;
import com.example.clientsservice.service.impl.ClientServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("clients")
public class ClientController {

    private final ClientServiceImp clientServiceImp;

    @Autowired
    public ClientController(ClientServiceImp clientServiceImp) {
        this.clientServiceImp = clientServiceImp;
    }

    @GetMapping()
    public List<ShortClientDto> geAll() {
        return this.clientServiceImp.getAll();
    }
}
