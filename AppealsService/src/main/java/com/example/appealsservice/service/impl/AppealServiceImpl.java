package com.example.appealsservice.service.impl;

import com.example.appealsservice.domain.Appeal;
import com.example.appealsservice.domain.StatusAppeal;
import com.example.appealsservice.domain.TNVED;
import com.example.appealsservice.dto.request.AppealRequestDto;
import com.example.appealsservice.dto.request.FilterAppealDto;
import com.example.appealsservice.dto.response.AppealDto;

import com.example.appealsservice.dto.response.ShortAppealDto;
import com.example.appealsservice.exception.NotRightsException;
import com.example.appealsservice.exception.ResourceNotFoundException;
import com.example.appealsservice.httpModel.ClientModel;
import com.example.appealsservice.repository.AppealRepository;
import com.example.appealsservice.repository.FileRepository;
import com.example.appealsservice.repository.ThemeRepository;
import com.example.appealsservice.repository.TNVEDRepository;
import com.example.appealsservice.service.AppealService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppealServiceImpl implements AppealService {

    private final AppealRepository appealRepository;
    private final TNVEDRepository tnvedRepository;
    private final FileServiceImpl fileServiceImpl;
    private final ThemeRepository themeRepository;

    public AppealServiceImpl(AppealRepository appealRepository, ThemeRepository themeRepository,
                             FileRepository fileRepository, FileServiceImpl fileServiceImpl,
                             TNVEDRepository tnvedRepository) {
        this.appealRepository = appealRepository;
        this.themeRepository = themeRepository;
        this.tnvedRepository = tnvedRepository;
        this.fileServiceImpl = fileServiceImpl;
    }

    /**
     * получение списка обращений
     */
    @Override
    public List<ShortAppealDto> getAll() {

        return appealRepository
                .findAll()
                .stream()
                .sorted(Comparator.comparing(Appeal::getCreateDate, Comparator.reverseOrder()))
                .map(ShortAppealDto::new)
                .collect(Collectors.toList());
    }

    /**
     * получение обращения по id
     */
    @Override
    public AppealDto getById(Long id) {
        var appeal = appealRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException(id));

        var files = fileServiceImpl.getFilesByAppealId(id);

        return new AppealDto(appeal, files);
    }

    /**
     * создание обращения
     */
    @Override
    public AppealDto create(List<MultipartFile> files, ClientModel client, AppealRequestDto request) throws IOException {

        var theme = themeRepository.findById(request.themeId).orElseThrow(()
                -> new ResourceNotFoundException(request.themeId));

        var appeal = new Appeal();
        appeal.setTheme(theme);

        if(request.startDate != null && request.endDate != null)
        {
            if(request.startDate.after(request.endDate))
            throw new NotRightsException("Incorrect date");
        }

        if(request.tnvedId != null || request.tnvedId != 0)
        {
           var tnved = tnvedRepository.findById(request.tnvedId).orElseThrow(()
                    -> new ResourceNotFoundException(request.tnvedId));
            appeal.setTnved(tnved);
        }
        appeal.setCreateDate(new Date());
        appeal.setTheme(theme);
        appeal.setStartDate(request.startDate);
        appeal.setEndDate(request.endDate);
        appeal.setAmount(request.amount);
        appeal.setEmail(client.getEmail());
        appeal.setNameOrg(client.getName());
        appeal.setDescription(request.description);
        appeal.setStatusAppeal(StatusAppeal.NOTPROCCESING);
        appeal.setClientId(client.getId());


        if (files != null && !files.isEmpty()) {

            for (MultipartFile f :
                    files) {
                fileServiceImpl.store(f, appeal.getId(), appeal.getClientId());
            }

        }
        appealRepository.save(appeal);

        return new AppealDto(appeal, fileServiceImpl.getFilesByAppealId(appeal.getId()));
    }


    /**
     * удаление обращения
     */
    @Override
    public void delete(Long id) {
        var appeal = appealRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException(id));

        appealRepository.delete(appeal);
    }


    /**
     * обновление обращения
     */
    @Override
    public AppealDto updateMyAppeal(List<MultipartFile> files, Long clientId, Long id, AppealRequestDto request) throws IOException {

        var appeal = appealRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException(id));

        if(appeal.getClientId() != clientId)
            throw new NotRightsException("This appeal is not yours");
        if(appeal.getStatusAppeal() != StatusAppeal.NOTPROCCESING)
            throw new NotRightsException("This appeal have already begun to consider");

        if(request.tnvedId != null)
        {
            var theme = themeRepository.findById(request.themeId).orElseThrow(()
                    -> new ResourceNotFoundException(request.themeId));
            appeal.setTheme(theme);
        }


        if(request.startDate != null && request.endDate != null)
        {
            if(request.startDate.after(request.endDate))
                throw new NotRightsException("Incorrect date");

            appeal.setStartDate(request.startDate);
            appeal.setEndDate(request.endDate);
        }

        if(request.tnvedId != null && request.tnvedId > 0)
        {
            var tnved = tnvedRepository.findById(request.tnvedId).orElseThrow(()
                    -> new ResourceNotFoundException(request.tnvedId));
            appeal.setTnved(tnved);
        }

        appeal.setAmount(request.amount);

        appeal.setUpdateDate(new Date());
        appeal.setDescription(request.description);

        if (files != null && !files.isEmpty()) {

            for (MultipartFile f :
                    files) {
                fileServiceImpl.store(f, appeal.getId(), appeal.getClientId());
            }

        }
        appealRepository.save(appeal);


        return new AppealDto(appeal, fileServiceImpl.getFilesByAppealId(appeal.getId()));

    }

    @Override
    public AppealDto update(List<MultipartFile> files, Long id, AppealRequestDto request) throws IOException {
        var appeal = appealRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException(id));

        if(request.tnvedId != null)
        {
            var theme = themeRepository.findById(request.themeId).orElseThrow(()
                    -> new ResourceNotFoundException(request.themeId));
            appeal.setTheme(theme);
        }


        if(request.startDate != null && request.endDate != null)
        {
            if(request.startDate.after(request.endDate))
                throw new NotRightsException("Incorrect date");

            appeal.setStartDate(request.startDate);
            appeal.setEndDate(request.endDate);
        }

        if(request.tnvedId != null && request.tnvedId > 0)
        {
            var tnved = tnvedRepository.findById(request.tnvedId).orElseThrow(()
                    -> new ResourceNotFoundException(request.tnvedId));
            appeal.setTnved(tnved);
        }

        appeal.setAmount(request.amount);

        appeal.setUpdateDate(new Date());
        appeal.setDescription(request.description);

        if (files != null && !files.isEmpty()) {

            for (MultipartFile f :
                    files) {
                fileServiceImpl.store(f, appeal.getId(), appeal.getClientId());
            }

        }

        appealRepository.save(appeal);

        return new AppealDto(appeal, fileServiceImpl.getFilesByAppealId(appeal.getId()));

    }

    /**
     * получение списка обращений с помощью фильтра
     */
    @Override
    public List<AppealDto> filter(FilterAppealDto filter) {

        var appeals = appealRepository
                .findAll()
                .stream();


        if (filter.themeId != null && filter.themeId != 0)
            appeals.filter(x -> x.getTheme().getId().equals(filter.themeId));


        if (filter.statusAppeal != null)
            appeals.filter(x -> x.getStatusAppeal() == filter.statusAppeal);

        if (filter.date != null)
            appeals.filter(x -> x.getCreateDate().after(filter.date));


        return appeals.collect(Collectors.toList())
                .stream()
                .map(x -> new AppealDto(x, fileServiceImpl.getFilesByAppealId(x.getId())))
                .collect(Collectors.toList());
    }

    /**
     * получение списка обращений клиента
     */
    @Override
    public List<AppealDto> myAppeals(Long clientId) {

        return appealRepository
                .findByClientId(clientId, Sort.by(Sort.Direction.DESC, "createDate") )
                .stream()
                .map(x -> new AppealDto(x, fileServiceImpl.getFilesByAppealId(x.getId())))
                .collect(Collectors.toList());
    }
}
