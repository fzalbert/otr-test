package com.example.clientsservice.controller;

import com.example.clientsservice.dto.request.ClientDto;
import com.example.clientsservice.dto.responce.ShortClientDto;
import com.example.clientsservice.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Scope("prototype")
@RestController
@RequestMapping("clients")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }


    @GetMapping("list")
    public List<ShortClientDto> geAll() {

        return this.clientService.getAll();
    }

    @GetMapping("by-id")
    public ClientDto getById(@RequestParam long id){

        return this.clientService.getById(id);
    }

    @PutMapping("update")
    public ClientDto update(@RequestBody @Valid ClientDto clientrequest) {

        return  this.clientService.update(clientrequest);
    }

    @DeleteMapping("delete")
    public boolean deleteById(@RequestParam long id){

        return this.clientService.deleteById(id);
    }

    @PutMapping("change-password")
    public boolean changePassword(@RequestParam long id, @RequestParam(required = true)String newPassword){
        return this.clientService.changePassword(id, newPassword);
    }
}
