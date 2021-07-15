package com.example.clientsservice.controller;

import com.example.clientsservice.service.ClientService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

@Log4j
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
        log.debug("Request method: for ban/block  UserId= " + id );
        this.clientService.blockById(id);
    }

    @GetMapping("unblock")
    public void unblockById(@RequestParam long id) {
        log.debug("Request method: for ban/unblock  UserId= " + id );
        this.clientService.unblockById(id);
    }
}
