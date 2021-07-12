package com.example.appealsservice.controller;

import com.example.appealsservice.dto.response.ThemeDto;
import com.example.appealsservice.exception.NotRightsException;
import com.example.appealsservice.httpModel.CheckUser;
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
@RequestMapping("theme")
public class ThemeController extends AuthorizeController{

    private final ThemeService themeService;
    private final CheckUser checkUser;

    @Autowired
    public ThemeController(ThemeService themeService,CheckUser checkUser, HttpServletRequest request) {
        super(request);
        this.themeService = themeService;
        this.checkUser = checkUser;
    }

    @GetMapping("list")
    public List<ThemeDto> getAll() {
        return themeService.getAll();
    }

    @GetMapping("/{name}")
    public  ThemeDto create(@PathVariable String name){
        if(!checkUser.isAdmin(userModel))
            throw new NotRightsException("You not admin");
        return themeService.Create(name);
    }

    @GetMapping("/{id}")
    public ThemeDto byId( @PathVariable Long id){
        return themeService.getById(id);
    }

    @GetMapping("/{id},{name}")
    public ThemeDto update(@PathVariable Long id, @PathVariable String name){
        return themeService.update(id, name);
    }
}
