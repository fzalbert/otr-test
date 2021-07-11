package com.example.appealsservice.service;

import com.example.appealsservice.dto.request.AppealRequestDto;
import com.example.appealsservice.dto.request.FilterAppealDto;
import com.example.appealsservice.dto.request.UpdateAppealRequestDto;
import com.example.appealsservice.dto.response.AppealDto;
import com.example.appealsservice.dto.response.ShortAppealDto;
import com.example.appealsservice.httpModel.ClientModel;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AppealService {

    List<ShortAppealDto> getAll();

    AppealDto getById(Long id);

    AppealDto create(List<MultipartFile> files, ClientModel client, AppealRequestDto request) throws IOException;

    public void delete(Long id);

    AppealDto updateMyAppeal(List<MultipartFile> files, Long clientId, Long id, AppealRequestDto request) throws IOException;

    AppealDto update(List<MultipartFile> files, Long id, AppealRequestDto request) throws IOException;

    List<AppealDto> filter(FilterAppealDto filter);

    List<AppealDto> myAppeals(Long clientId);

}
