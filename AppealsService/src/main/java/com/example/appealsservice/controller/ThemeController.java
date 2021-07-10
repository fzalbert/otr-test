package com.example.appealsservice.controller;

import com.example.appealsservice.dto.response.ThemeDto;
import com.example.appealsservice.service.impl.ThemeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("themes")
public class ThemeController extends AuthorizeController{

    private final ThemeServiceImpl themeServiceImpl;

    @Autowired
    public ThemeController(ThemeServiceImpl themeServiceImpl, HttpServletRequest request) {
        super(request);
        this.themeServiceImpl = themeServiceImpl;
    }

    @GetMapping()
    public List<ThemeDto> getAll() {
        return themeServiceImpl.getAll();
    }

    @GetMapping("/{name}")
    public  ThemeDto create( String name){
        return themeServiceImpl.Create(name);
    }

    @GetMapping("/{id}")
    public ThemeDto byId( Long id){
        return themeServiceImpl.getById(id);
    }

    @GetMapping("/{id},{name}")
    public ThemeDto update( Long id, String name){
        return themeServiceImpl.update(id, name);
    }
}
