package com.example.appealsservice.controller;

import com.example.appealsservice.dto.response.TNVEDDto;
import com.example.appealsservice.service.TNVEDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@Scope("prototype")
@RestController
@RequestMapping("tnved")
public class TNVEDController extends AuthorizeController {

    private final TNVEDService tnvedService;

    @Autowired
    public TNVEDController(TNVEDService tnvedService, HttpServletRequest request,
                           @Value("${security.jwt.parse.url}") String jwtParseUrl) {
        super(request, jwtParseUrl);
        this.tnvedService = tnvedService;
    }

    /**
     * Получить список ТН ВЕД
     */
    @GetMapping("list")
    public List<TNVEDDto> getAll() {

        return tnvedService.getAll();
    }

    /**
     * Получить ТН ВЕД по id
     * @param id
     */
    @GetMapping("by-id")
    public TNVEDDto byId(@RequestParam Long id) {

        return tnvedService.byId(id);
    }

    /**
     * Иницилизировать список ТН ВЕД
     */
    @GetMapping("/init")
    public void initTnveds() throws IOException, URISyntaxException {
        tnvedService.init();
    }
}
