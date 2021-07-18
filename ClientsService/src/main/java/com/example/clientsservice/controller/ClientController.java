package com.example.clientsservice.controller;

import com.example.clientsservice.dto.request.ClientDto;
import com.example.clientsservice.dto.responce.ShortClientDto;
import com.example.clientsservice.service.ClientService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Log4j
@Scope("prototype")
@RestController
@RequestMapping("clients")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }


    /**
     * Получение списка клиентов
     * @param request
     */
    @GetMapping("list")
    public List<ShortClientDto> geAll(HttpServletRequest request) {
        return this.clientService.getAll();
    }

    /**
     * Получение клиента по id
     * @param id
     */
    @GetMapping("by-id")
    public ClientDto getById(@RequestParam long id) {
        return this.clientService.getById(id);
    }

    /**
     * Обновить клиента
     * @param request
     */
    @PostMapping("update")
    public ClientDto update(@RequestBody @Valid ClientDto request) {
        log.debug("Request method: clients/update user: " + request.getLogin());
        return this.clientService.update(request);
    }

    /**
     * Удалить клиента
     * @param id
     */
    @DeleteMapping("delete")
    public boolean deleteById(@RequestParam long id) {
        log.debug("Request method: clients/delete userId: " + id);
        return this.clientService.deleteById(id);
    }

    /**
     * Сменить пароль
     * @param id
     * @param newPassword
     */
    @PutMapping("change-password")
    public boolean changePassword(@RequestParam long id, @RequestParam(required = true) String newPassword) {
        log.debug("Request method: clients/change-password userId: " + id);
        return this.clientService.changePassword(id, newPassword);
    }
}
