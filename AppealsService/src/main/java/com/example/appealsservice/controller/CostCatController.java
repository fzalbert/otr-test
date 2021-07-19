package com.example.appealsservice.controller;

import com.example.appealsservice.dto.response.CostCatDto;
import com.example.appealsservice.dto.response.TNVEDDto;
import com.example.appealsservice.service.CostCatService;
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
@RequestMapping("catcost")
public class CostCatController extends AuthorizeController {

    private final CostCatService costCatService;

    @Autowired
    public CostCatController(CostCatService costCatService, HttpServletRequest request,
                             @Value("${security.jwt.parse.url}") String jwtParseUrl) {
        super(request, jwtParseUrl);
        this.costCatService = costCatService;
    }

    /**
     * Получить список категорий затрат
     */
    @GetMapping("list")
    public List<CostCatDto> getAll() {

        return costCatService.getAll();
    }

    /**
     * Получить категорию затрат по id
     * @param id
     */
    @GetMapping("by-id")
    public CostCatDto byId(@RequestParam Long id) {

        return costCatService.byId(id);
    }

    /**
     * Инициализировать категории затрат
     */
    @GetMapping("/init")
    public void initCats() throws IOException, URISyntaxException {
        costCatService.init();
    }
}
