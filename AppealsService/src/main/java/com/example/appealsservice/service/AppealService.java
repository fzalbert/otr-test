package com.example.appealsservice.service;

import com.example.appealsservice.domain.Appeal;
import com.example.appealsservice.dto.request.AppealRequestDto;
import com.example.appealsservice.dto.request.FilterAppealDto;
import com.example.appealsservice.dto.response.AppealDto;
import com.example.appealsservice.dto.response.ThemeDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppealService {

    List<AppealDto> getAll();

    AppealDto getById(long id);

    AppealDto Create(long clientId, AppealRequestDto request);

    void Delete(long id);

    AppealDto update(long clientId, long id, AppealRequestDto request);

    List<AppealDto> filter(FilterAppealDto filter);

    List<AppealDto> myAppeals(long clientId);

}
