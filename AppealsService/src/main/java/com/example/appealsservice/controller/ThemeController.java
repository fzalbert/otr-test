package com.example.appealsservice.controller;

import com.example.appealsservice.dto.response.ThemeDto;
import com.example.appealsservice.exception.NotRightsException;
import com.example.appealsservice.httpModel.CheckUser;
import com.example.appealsservice.service.ThemeService;
import com.example.appealsservice.service.impl.ThemeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Scope("prototype")
@RestController
@RequestMapping("theme")
public class ThemeController extends AuthorizeController{

    private final ThemeService themeService;
    private final CheckUser checkUser;

    @Autowired
    public ThemeController(ThemeService themeService,CheckUser checkUser, HttpServletRequest request,
                           @Value("${security.jwt.parse.url}") String jwtParseUrl) {
        super(request, jwtParseUrl);
        this.themeService = themeService;
        this.checkUser = checkUser;
    }

    /**
     * Получить список тем
     */
    @GetMapping("list")
    public List<ThemeDto> getAll() {
        return themeService.getAll();
    }

    /**
     * Создать тему
     * @param name
     */
    @GetMapping("create")
    public  ThemeDto create(@RequestParam String name){
        if(!checkUser.isAdmin(userModel))
            throw new NotRightsException("You not admin");
        return themeService.Create(name);
    }

    /**
     * Получить тему по id
     * @param id
     */
    @GetMapping("by-id")
    public ThemeDto byId( @RequestParam Long id){
        return themeService.getById(id);
    }

    /**
     * Обновить тему
     * @param id
     * @param name
     */
    @GetMapping("update")
    public ThemeDto update(@RequestParam Long id, @RequestParam String name){
        return themeService.update(id, name);
    }
}
