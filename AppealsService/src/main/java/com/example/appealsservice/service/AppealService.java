package com.example.appealsservice.service;

import com.example.appealsservice.dto.request.AppealRequestDto;
import com.example.appealsservice.dto.request.FilterAppealDto;
import com.example.appealsservice.dto.response.AppealDto;
import com.example.appealsservice.dto.response.ShortAppealDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AppealService {

    List<ShortAppealDto> getAll();

    AppealDto getById(long id);

    AppealDto create(List<MultipartFile> files, AppealRequestDto request) throws IOException;

    void delete(long id);

    AppealDto updateMyAppeal(long clientId, long id, AppealRequestDto request);

    List<AppealDto> filter(FilterAppealDto filter);

    List<AppealDto> myAppeals(long clientId);

}
