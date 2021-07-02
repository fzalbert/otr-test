package com.example.appealsservice.controller;

import com.example.appealsservice.dto.request.AppealRequestDto;
import com.example.appealsservice.dto.response.AppealDto;
import com.example.appealsservice.dto.response.ThemeDto;
import com.example.appealsservice.service.impl.AppealServiceImpl;
import com.example.appealsservice.service.impl.ThemeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("appeals")
public class AppealController {

    private final AppealServiceImpl appealServiceImpl;

    @Autowired
    public AppealController(AppealServiceImpl appealServiceImpl) {
        this.appealServiceImpl = appealServiceImpl;
    }

    @GetMapping()
    public List<AppealDto> getAll() {
        return this.appealServiceImpl.getAll();
    }

    @GetMapping("/{id}")
    public AppealDto ById(long id){
        return this.appealServiceImpl.getById(id);
    }

    @PostMapping("/create")
    public AppealDto Create(long clientId, @RequestBody AppealRequestDto request){
        return this.appealServiceImpl.Create(clientId, request);
    }

}
