package com.example.clientsservice.controller;

import com.example.clientsservice.dto.request.ClientDto;
import com.example.clientsservice.dto.request.CreateClientDto;
import com.example.clientsservice.dto.responce.ShortClientDto;
import com.example.clientsservice.service.impl.ClientServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("clients")
public class ClientController {

    private final ClientServiceImp clientServiceImp;

    @Autowired
    public ClientController(ClientServiceImp clientServiceImp) {
        this.clientServiceImp = clientServiceImp;
    }

    @PostMapping("register")
    public Boolean register(@RequestBody @Valid CreateClientDto request){return  this.clientServiceImp.register((request));}

    @GetMapping("auth")
    public Boolean auth(String login, String password) {
        return this.clientServiceImp.auth(login, password);
    }

    @GetMapping("GetAll")
    public List<ShortClientDto> geAll() {
        return this.clientServiceImp.getAll();
    }

    @PutMapping("BlockById")
    public void blockById(long id){ this.clientServiceImp.blockById(id);};

    @PutMapping("UnBlockById")
    public void unblockById(long id){this.clientServiceImp.unblockById(id);}

    @GetMapping("getById")
    public ClientDto getById(long id){return this.clientServiceImp.getById(id);}

    @PutMapping("update")
    public ClientDto update(@RequestBody @Valid ClientDto clientrequest) {return  this.clientServiceImp.update(clientrequest);}

    @DeleteMapping("DeleteById")
    public boolean deleteById(long id){return this.clientServiceImp.deleteById(id);}

    @PutMapping("ChangePassword")
    public boolean changePassword(long id, String newPassword){this.clientServiceImp.changePassword(id, newPassword);
    return true;}
}
