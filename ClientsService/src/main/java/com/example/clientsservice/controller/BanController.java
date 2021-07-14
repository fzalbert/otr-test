package com.example.clientsservice.controller;

import com.example.clientsservice.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

@Scope("prototype")
@RestController
@RequestMapping("ban")
public class BanController {

    private final ClientService clientService;

    @Autowired
    public BanController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("block")
    public void blockById(@RequestParam long id) {

        this.clientService.blockById(id);
    }

    @PutMapping("unblock")
    public void unblockById(@RequestParam long id) {

        this.clientService.unblockById(id);
    }
}
