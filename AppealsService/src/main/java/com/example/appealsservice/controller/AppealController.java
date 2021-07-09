package com.example.appealsservice.controller;

import com.example.appealsservice.dto.request.AppealRequestDto;
import com.example.appealsservice.dto.request.FilterAppealDto;
import com.example.appealsservice.dto.response.AppealDto;
import com.example.appealsservice.dto.response.FileDto;
import com.example.appealsservice.dto.response.ShortAppealDto;
import com.example.appealsservice.dto.response.ThemeDto;
import com.example.appealsservice.service.impl.AppealServiceImpl;
import com.example.appealsservice.service.impl.ThemeServiceImpl;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiParam;
import jdk.jfr.ContentType;

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
    public AppealDto byId(Long id){
        return appealServiceImpl.getById(id);
    }

    @PostMapping("/filter")
    public List<AppealDto> byId(@RequestBody FilterAppealDto request){
        return appealServiceImpl.filter(request);
    }

    @GetMapping("/byClientId/{id}")
    public List<AppealDto> byClientId(Long clientId){
        return appealServiceImpl.myAppeals(clientId);
    }

    @DeleteMapping("/{id}")
    public void delete(Long id){
        appealServiceImpl.delete(id);
    }


    @RequestMapping(value = "/create", method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_JSON_VALUE,
                        MediaType.MULTIPART_FORM_DATA_VALUE})
    public AppealDto create( @RequestParam("request") String request,
                            @RequestParam(value = "file", required = false) List<MultipartFile> files) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        var appeal = objectMapper.readValue(request, AppealRequestDto.class);
        return appealServiceImpl.create(files, appeal);
    }


}
