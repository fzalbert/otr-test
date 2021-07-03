package com.example.appealsservice.controller;

import com.example.appealsservice.dto.request.AppealRequestDto;
import com.example.appealsservice.dto.response.AppealDto;
import com.example.appealsservice.dto.response.FileDto;
import com.example.appealsservice.dto.response.ThemeDto;
import com.example.appealsservice.service.impl.AppealServiceImpl;
import com.example.appealsservice.service.impl.ThemeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
        return appealServiceImpl.getAll();
    }

    @GetMapping("/{id}")
    public AppealDto byId(long id){
        return appealServiceImpl.getById(id);
    }

    @PostMapping("/create")
    public AppealDto create(long clientId, @RequestBody AppealRequestDto request){
        return appealServiceImpl.create(clientId, request);
    }



}
