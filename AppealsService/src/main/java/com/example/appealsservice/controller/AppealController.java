package com.example.appealsservice.controller;

import com.example.appealsservice.domain.enums.TaskStatus;
import com.example.appealsservice.dto.request.AppealRequestDto;
import com.example.appealsservice.dto.request.FilterAppealDto;
import com.example.appealsservice.dto.response.AppealDto;
import com.example.appealsservice.dto.response.ShortAppealDto;
import com.example.appealsservice.exception.MissingRequiredFieldException;
import com.example.appealsservice.exception.NotRightsException;
import com.example.appealsservice.httpModel.CheckUser;
import com.example.appealsservice.service.AppealService;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Scope("prototype")
@RestController
@RequestMapping("appeals")
public class AppealController  extends AuthorizeController{

    private final AppealService appealService;
    private final CheckUser checkUser;

    @Autowired
    public AppealController(AppealService appealService, CheckUser checkUser,
                            HttpServletRequest servletRequest) {
        super(servletRequest);
        this.appealService = appealService;
        this.checkUser = checkUser;
    }

    @GetMapping("short-list")
    public List<ShortAppealDto> getAll() {
        return appealService.getAll();
    }

    @GetMapping("by-id")
    public AppealDto byId(@RequestParam Long id){
        return appealService.getById(id);
    }

    @PostMapping("filter")
    public List<ShortAppealDto> filer(@RequestBody(required = false) FilterAppealDto request){
        return appealService.filter(request);
    }

    @GetMapping("by-client-id")
    public List<ShortAppealDto> byClientId(@RequestParam Long clientId){
        if(!checkUser.isClient(userModel))
            throw new NotRightsException("");
        return appealService.myAppeals(clientId);
    }

    @DeleteMapping("delete")
    public void delete(@RequestParam Long id){
        appealService.deleteById(id);
    }

    @GetMapping("check")
    public void check(@RequestParam Long id, @RequestParam TaskStatus status){
        appealService.check(id, status);
    }

    @RequestMapping(value = "update-my", method = RequestMethod.POST,
                        consumes = {MediaType.APPLICATION_JSON_VALUE})
    public AppealDto updateMy( @RequestParam("request") String request,
                             @RequestParam("appeal-id") Long appealId) throws JsonProcessingException {

        if(!checkUser.isClient(userModel))
            throw new NotRightsException("This is not your appeal");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        var appealRequest = objectMapper.readValue(request, AppealRequestDto.class);

        return appealService.updateMyAppeal(userModel.getId(), appealId, appealRequest);
    }

    @RequestMapping(value = "update", method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public AppealDto update( @RequestParam("request") String request,
                             @RequestParam("appeal-id") Long appealId) throws JsonProcessingException {

        if(!checkUser.isClient(userModel))
            throw new NotRightsException("This is not your appeal");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        var appealRequest = objectMapper.readValue(request, AppealRequestDto.class);

        return appealService.update(userModel.getId(), appealId, appealRequest);
    }


    @RequestMapping(value = "create", method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_JSON_VALUE,
                        MediaType.MULTIPART_FORM_DATA_VALUE})
    public AppealDto create( @RequestParam("request") String request,
                            @RequestParam(value = "file", required = false) List<MultipartFile> files) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        var appeal = objectMapper.readValue(request, AppealRequestDto.class);

        return appealService.create(files, userModel, appeal);
    }
}
