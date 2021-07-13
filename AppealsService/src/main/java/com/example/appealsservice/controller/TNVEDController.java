package com.example.appealsservice.controller;

import com.example.appealsservice.dto.response.TNVEDDto;
import com.example.appealsservice.service.TNVEDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public TNVEDController(TNVEDService tnvedService, HttpServletRequest request) {
        super(request);
        this.tnvedService = tnvedService;
    }

    @GetMapping("/list")
    public List<TNVEDDto> getAll() {

        return tnvedService.getAll();
    }

    @GetMapping("/list")
    public TNVEDDto byId(Long id) {

        return tnvedService.byId(id);
    }

    @GetMapping("/init")
    public void initTnveds() throws IOException, URISyntaxException {
        tnvedService.init();
    }
}
