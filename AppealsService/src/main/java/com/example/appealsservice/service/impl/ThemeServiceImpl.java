package com.example.appealsservice.service.impl;

import com.example.appealsservice.domain.Theme;
import com.example.appealsservice.dto.response.ThemeDto;
import com.example.appealsservice.exception.ResourceNotFoundException;
import com.example.appealsservice.repository.ThemeRepository;
import com.example.appealsservice.service.ThemeService;
import com.sun.istack.NotNull;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ThemeServiceImpl implements ThemeService {

    private final ThemeRepository themeRepository;

    public ThemeServiceImpl(ThemeRepository themeRepository)
    {
        this.themeRepository = themeRepository;
    }

    @Override
    public List<ThemeDto> getAll() {

        return themeRepository
                .findAll()
                .stream()
                .sorted(Comparator.comparing(Theme::getName, Comparator.reverseOrder()))
                .map(ThemeDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public ThemeDto getById(long id) {
        var theme = themeRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException(id));

        return new ThemeDto(theme);

    }

    @Override
    public ThemeDto Create(String name) {

        Theme theme = new Theme();
        theme.setName(name);
        themeRepository.save(theme);

        return new ThemeDto(theme);

    }

    @Override
    public void Delete(long id) {

        var theme = themeRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException(id));

        themeRepository.delete(theme);

    }

    @Override
    public ThemeDto update(Long id, @NotNull String name) {

        var theme = themeRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException(id));

        theme.setName(name);
        themeRepository.save(theme);

        return new ThemeDto(theme);
    }
}
