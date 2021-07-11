package com.example.appealsservice.controller;

import com.example.appealsservice.dto.response.ThemeDto;
import com.example.appealsservice.service.ThemeService;
import com.example.appealsservice.service.impl.ThemeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Scope("prototype")
@RestController
@RequestMapping("themes")
public class ThemeController extends AuthorizeController{

    private final ThemeService themeService;

    @Autowired
    public ThemeController(ThemeService themeService, HttpServletRequest request) {
        super(request);
        this.themeService = themeService;
    }

    @GetMapping("/getAll")
    public List<ThemeDto> getAll() {
        return themeService.getAll();
    }

    @GetMapping("/{name}")
    public  ThemeDto create( String name){
        return themeService.Create(name);
    }

    @GetMapping("/{id}")
    public ThemeDto byId( Long id){
        return themeService.getById(id);
    }

    @GetMapping("/{id},{name}")
    public ThemeDto update( Long id, String name){
        return themeService.update(id, name);
    }
}
