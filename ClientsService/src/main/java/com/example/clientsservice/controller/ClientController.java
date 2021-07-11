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

    @PostMapping("register")
    public long register(@RequestBody @Valid CreateClientDto request){return  this.clientService.register((request));}

    @GetMapping("auth")
    public ClientModelDto auth(AuthDto request) {
        return this.clientService.auth(request);
    }

    @GetMapping("get-all")
    public List<ShortClientDto> geAll(HttpServletRequest request) {
        return this.clientService.getAll();
    }

    @PutMapping("block_by_id")
    public void blockById(long id){ this.clientService.blockById(id);};

    @PutMapping("un_block_by_id")
    public void unblockById(long id){this.clientService.unblockById(id);}

    @GetMapping("get_by_id")
    public ClientDto getById(long id){return this.clientService.getById(id);}

    @PutMapping("update")
    public ClientDto update(@RequestBody @Valid ClientDto clientrequest) {return  this.clientService.update(clientrequest);}

    @DeleteMapping("delete_by_id")
    public boolean deleteById(long id){return this.clientService.deleteById(id);}

    @PutMapping("change_password")
    public boolean changePassword(long id, String newPassword){return this.clientService.changePassword(id, newPassword);}
}
