package com.example.appealsservice.service.impl;

import com.example.appealsservice.domain.Appeal;
import com.example.appealsservice.domain.StatusAppeal;
import com.example.appealsservice.dto.request.AppealRequestDto;
import com.example.appealsservice.dto.request.FilterAppealDto;
import com.example.appealsservice.dto.response.AppealDto;

import com.example.appealsservice.exception.ResourceNotFoundException;
import com.example.appealsservice.repository.AppealRepository;
import com.example.appealsservice.repository.ThemeRepository;
import com.example.appealsservice.service.AppealService;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class AppealServiceImpl implements AppealService {

    private final AppealRepository appealRepository;
    private final ThemeRepository themeRepository;

    public AppealServiceImpl(AppealRepository appealRepository, ThemeRepository themeRepository)
    {
        this.appealRepository = appealRepository;
        this.themeRepository = themeRepository;
    }
    @Override
    public List<AppealDto> getAll() {

        return appealRepository
                .findAll()
                .stream()
                .sorted(Comparator.comparing(Appeal::getCreateDate, Comparator.reverseOrder()))
                .map(AppealDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public AppealDto getById(long id) {
        var appeal = appealRepository.findById(id);
        if( appeal.isEmpty())
            return null;

        return  new AppealDto(appeal.get());


    }

    @Override
    public AppealDto Create(long clientId, AppealRequestDto request) {

        var theme = themeRepository.findById(request.themeId);
        if(theme.isEmpty())
            return  null;

        var appeal = new Appeal();
        appeal.setCreateDate(new Date());
        appeal.setTheme(theme.get());
        appeal.setDescription(request.description);
        appeal.setStatusAppeal(StatusAppeal.NotProcessed);
        appeal.setClientId(clientId);

        appealRepository.save(appeal);
        return new AppealDto(appeal);

    }


    @Override
    public void Delete(long id) {
        var appeal = appealRepository.findById(id);
        if( appeal.isEmpty())
            return;

        appealRepository.delete(appeal.orElseThrow());
    }

    @Override
    public AppealDto update(long userId, long id, AppealRequestDto request) {
        var appeal = appealRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException(id));
        var theme = themeRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException(id));
        appeal.setTheme(theme);

        appeal.setUpdateDate(new Date());
        appeal.setDescription(request.description);

        appealRepository.save(appeal);

        return new AppealDto(appeal);


    }

    @Override
    public List<AppealDto> filter(FilterAppealDto filter) {
        return null;
    }

    @Override
    public List<AppealDto> myAppeals(long clientId) {
        return appealRepository
                .findAll()
                .stream()
                .filter(x -> x.getClientId() == clientId)
                .sorted(Comparator.comparing(Appeal::getCreateDate, Comparator.reverseOrder()))
                .map(AppealDto::new)
                .collect(Collectors.toList());
    }
}
