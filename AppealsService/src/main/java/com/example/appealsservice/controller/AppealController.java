package com.example.appealsservice.controller;

import com.example.appealsservice.domain.enums.TaskStatus;
import com.example.appealsservice.dto.request.AppealRequestDto;
import com.example.appealsservice.dto.request.FilterAppealAdminDto;
import com.example.appealsservice.dto.request.FilterAppealDto;
import com.example.appealsservice.dto.response.AppealDto;
import com.example.appealsservice.dto.response.ShortAppealDto;
import com.example.appealsservice.exception.NotRightsException;
import com.example.appealsservice.httpModel.CheckUser;
import com.example.appealsservice.service.AppealService;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Log4j
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
        log.debug("Request method: appeal/by-id. UserId = " + userModel.getId());
        return appealService.getById(id);
    }

    @PostMapping("filter-for-client")
    public List<ShortAppealDto> filter(@RequestBody(required = false) FilterAppealDto request){
        log.debug("Request method: appeal/filter-for-client. UserId = " + userModel.getId());
        return appealService.filter(userModel.getId(), request);
    }

    @GetMapping("by-client-id")
    public List<ShortAppealDto> byClientId(){
        if(!checkUser.isClient(userModel))
            throw new NotRightsException("Нет прав");

        return appealService.myAppeals(userModel.getId());
    }

    @PostMapping("filter-for-admin")
    public List<ShortAppealDto> filterAdmin(@RequestBody(required = false) FilterAppealAdminDto request){
        log.debug("Request method: appeal/filter-for-admin. EmployeeId = " + userModel.getId());
        return appealService.filterAdmin(request);
    }

    @DeleteMapping("delete")
    public void delete(@RequestParam Long id){
        appealService.deleteById(id);
    }

    @GetMapping("check")
    public void check(@RequestParam Long id, @RequestParam int status){
        log.debug("Request method: appeal/check. EmployeeId = " + userModel.getId());
        appealService.check(id, TaskStatus.values()[status]);
    }

    @RequestMapping(value = "update-my")
    public AppealDto updateMy(@RequestBody AppealRequestDto request,
                             @RequestParam("appeal-id") Long appealId) {

        return appealService.updateMyAppeal(userModel.getId(), appealId, request);
    }

    @RequestMapping(value = "update")
    public AppealDto update( @RequestBody AppealRequestDto request,
                             @RequestParam("appeal-id") Long appealId) {

        log.debug("Request method: appeal/update. EmployeeId = " + userModel.getId());
        return appealService.update(userModel.getId(), appealId, request);
    }


    @RequestMapping(value = "create", method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_JSON_VALUE,
                        MediaType.MULTIPART_FORM_DATA_VALUE})
    public AppealDto create(@RequestParam("request") String request,
                            @RequestParam(value = "file", required = false) List<MultipartFile> files) throws IOException {

        log.debug("Request method: appeal/create. UserId = " + userModel.getId().toString());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        var appeal = objectMapper.readValue(request, AppealRequestDto.class);

        var response =  appealService.create(files, userModel, appeal);
        if(response == null)
            log.error("ERROR Request method : appeal/create. UserId = " + userModel.getId());

        log.error("SUCCESS Request method : appeal/create. UserId = " + userModel.getId());
        return response;
    }
}
