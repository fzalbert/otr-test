package com.example.appealsservice.controller;

import com.example.appealsservice.dto.response.ThemeDto;
import com.example.appealsservice.service.impl.ThemeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("themes")
public class ThemeController {

    private final ThemeServiceImpl themeServiceImpl;

    @Autowired
    public ThemeController(ThemeServiceImpl themeServiceImpl) {
        this.themeServiceImpl = themeServiceImpl;
    }

    @GetMapping()
    public List<ThemeDto> getAll() {
        return this.themeServiceImpl.getAll();
    }

    @GetMapping("/{name}")
    public  ThemeDto Create( String name){
        return this.themeServiceImpl.Create(name);
    }

    @GetMapping("/{id}")
    public ThemeDto ById( Long id){
        return this.themeServiceImpl.getById(id);
    }

    @GetMapping("/{id},{name}")
    public ThemeDto Update( Long id, String name){
        return this.themeServiceImpl.update(id, name);
    }
}
