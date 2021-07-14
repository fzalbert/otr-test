package com.example.appealsservice.service;

import com.example.appealsservice.domain.enums.TaskStatus;
import com.example.appealsservice.dto.request.AppealRequestDto;
import com.example.appealsservice.dto.request.FilterAppealAdminDto;
import com.example.appealsservice.dto.request.FilterAppealDto;
import com.example.appealsservice.dto.response.AppealDto;
import com.example.appealsservice.dto.response.ShortAppealDto;
import com.example.appealsservice.httpModel.UserModel;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AppealService {

    List<ShortAppealDto> getAll();

    AppealDto getById(Long id);

    AppealDto create(List<MultipartFile> files, UserModel client, AppealRequestDto request) throws IOException;

    public void deleteById(Long id);

    AppealDto updateMyAppeal( Long clientId, Long id, AppealRequestDto request);

    AppealDto update(Long employeeId, Long id, AppealRequestDto request);

    List<ShortAppealDto> filter(Long clientId, FilterAppealDto filter);

    List<ShortAppealDto> myAppeals(Long clientId);

    List<ShortAppealDto> filterAdmin(FilterAppealAdminDto filter);

    void check(Long id, TaskStatus status);
}
