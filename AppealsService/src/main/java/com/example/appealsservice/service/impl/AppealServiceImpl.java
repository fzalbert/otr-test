package com.example.appealsservice.service.impl;

import com.example.appealsservice.domain.Appeal;
import com.example.appealsservice.domain.File;
import com.example.appealsservice.domain.StatusAppeal;
import com.example.appealsservice.dto.request.AppealRequestDto;
import com.example.appealsservice.dto.request.FilterAppealDto;
import com.example.appealsservice.dto.response.AppealDto;

import com.example.appealsservice.dto.response.FileDto;
import com.example.appealsservice.exception.NotRightsException;
import com.example.appealsservice.exception.ResourceNotFoundException;
import com.example.appealsservice.repository.AppealRepository;
import com.example.appealsservice.repository.FileRepository;
import com.example.appealsservice.repository.ThemeRepository;
import com.example.appealsservice.service.AppealService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppealServiceImpl implements AppealService {

    private final AppealRepository appealRepository;
    private final FileRepository fileRepository;
    private final ThemeRepository themeRepository;

    public AppealServiceImpl(AppealRepository appealRepository, ThemeRepository themeRepository,
                             FileRepository fileRepository) {
        this.appealRepository = appealRepository;
        this.themeRepository = themeRepository;
        this.fileRepository = fileRepository;
    }

    /** получение списка обращений  */
    @Override
    public List<AppealDto> getAll() {

        return appealRepository
                .findAll()
                .stream()
                .sorted(Comparator.comparing(Appeal::getCreateDate, Comparator.reverseOrder()))
                .map(AppealDto::new)
                .collect(Collectors.toList());
    }

    /** получение обращения по id */
    @Override
    public AppealDto getById(long id) {
        var appeal = appealRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException(id));

        return new AppealDto(appeal);
    }

    /** создание обращения */
    @Override
    public AppealDto create(long clientId, AppealRequestDto request) {

        var theme = themeRepository.findById(request.themeId).orElseThrow(()
                -> new ResourceNotFoundException(request.themeId));

        var appeal = new Appeal();
        appeal.setCreateDate(new Date());
        appeal.setTheme(theme);
        appeal.setDescription(request.description);
        appeal.setStatusAppeal(StatusAppeal.NotProcessed);
        appeal.setClientId(clientId);

        appealRepository.save(appeal);

        var appealDto = new AppealDto(appeal);
        return appealDto;

    }

    /** удаление обращения */
    @Override
    public void delete(long id) {
        var appeal = appealRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException(id));

        appealRepository.delete(appeal);
    }

    /** обновление обращения  */
    @Override
    public AppealDto updateMyAppeal(long clientId, long id, AppealRequestDto request) {

        var appeal = appealRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException(id));

        var theme = themeRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException(id));

        if (appeal.getClientId() != clientId)
            throw new NotRightsException("");

        appeal.setTheme(theme);
        appeal.setUpdateDate(new Date());
        appeal.setDescription(request.description);

        appealRepository.save(appeal);

        return new AppealDto(appeal);

    }

    /** получение списка обращений с помощью фильтра*/
    @Override
    public List<AppealDto> filter(FilterAppealDto filter) {
        return null;
    }

    /** получение списка обращений клиента  */
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
