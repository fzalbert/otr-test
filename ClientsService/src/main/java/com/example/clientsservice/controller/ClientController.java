package com.example.clientsservice.controller;

import com.example.clientsservice.dto.request.AuthDto;
import com.example.clientsservice.dto.request.ClientDto;
import com.example.clientsservice.dto.request.CreateClientDto;
import com.example.clientsservice.dto.responce.ClientModelDto;
import com.example.clientsservice.dto.responce.ShortClientDto;
import com.example.clientsservice.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("clients")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }


    @GetMapping("/list")
    public List<ShortClientDto> geAll() {

        return this.clientService.getAll();
    }

    @GetMapping("/block")
    public void blockById(@RequestParam long id) {

        this.clientService.blockById(id);
    }

    @PutMapping("/unblock")
    public void unblockById(@RequestParam long id) {

        this.clientService.unblockById(id);
    }

    @GetMapping("id")
    public ClientDto getById(@RequestParam long id){

        return this.clientService.getById(id);
    }

    @PutMapping("update")
    public ClientDto update(@RequestBody @Valid ClientDto clientrequest) {

        return  this.clientService.update(clientrequest);
    }

    @DeleteMapping("id")
    public boolean deleteById(@RequestParam long id){

        return this.clientService.deleteById(id);
    }

    @PutMapping("change-password")
    public boolean changePassword(@RequestParam long id, @RequestParam(required = true)String newPassword){
        return this.clientService.changePassword(id, newPassword);
    }
}
