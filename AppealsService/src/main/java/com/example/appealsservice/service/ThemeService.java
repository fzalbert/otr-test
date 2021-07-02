package com.example.appealsservice.service;

import com.example.appealsservice.domain.Theme;
import com.example.appealsservice.dto.response.ThemeDto;

import java.util.List;

public interface ThemeService {

    List<ThemeDto> getAll();

    ThemeDto getById(long id);

    ThemeDto Create(String name);

    void Delete(long id);

    ThemeDto update(Long id, String name);

}
