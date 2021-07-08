package com.example.appealsservice.controller;

import com.example.appealsservice.dto.request.AppealRequestDto;
import com.example.appealsservice.dto.response.AppealDto;
import com.example.appealsservice.dto.response.FileDto;
import com.example.appealsservice.dto.response.ShortAppealDto;
import com.example.appealsservice.dto.response.ThemeDto;
import com.example.appealsservice.service.impl.AppealServiceImpl;
import com.example.appealsservice.service.impl.ThemeServiceImpl;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    public List<ShortAppealDto> getAll() {
        return appealServiceImpl.getAll();
    }

    @GetMapping("/{id}")
    public AppealDto byId(long id){
        return appealServiceImpl.getById(id);
    }


    @PostMapping("/create")
    public AppealDto create(@RequestBody AppealRequestDto request,
                            @RequestPart("file") @ApiParam(value="File", required=true) MultipartFile file) throws IOException {
        return appealServiceImpl.create(file, request);
    }

}
