package com.example.appealsservice.service.impl;

import com.example.appealsservice.domain.Appeal;
import com.example.appealsservice.domain.StatusAppeal;
import com.example.appealsservice.dto.request.AppealRequestDto;
import com.example.appealsservice.dto.request.FilterAppealDto;
import com.example.appealsservice.dto.response.AppealDto;

import com.example.appealsservice.dto.response.ShortAppealDto;
import com.example.appealsservice.exception.NotRightsException;
import com.example.appealsservice.exception.ResourceNotFoundException;
import com.example.appealsservice.repository.AppealRepository;
import com.example.appealsservice.repository.FileRepository;
import com.example.appealsservice.repository.ThemeRepository;
import com.example.appealsservice.service.AppealService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AppealServiceImpl implements AppealService {

    private final AppealRepository appealRepository;
    private final FileServiceImpl fileServiceImpl;
    private final ThemeRepository themeRepository;

    public AppealServiceImpl(AppealRepository appealRepository, ThemeRepository themeRepository,
                             FileRepository fileRepository, FileServiceImpl fileServiceImpl) {
        this.appealRepository = appealRepository;
        this.themeRepository = themeRepository;
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
    public AppealDto getById(long id) {
        var appeal = appealRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException(id));

        var files = fileServiceImpl.getFilesByAppealId(id);

        return new AppealDto(appeal, files);
    }

    /**
     * создание обращения
     */
    @Override
    public AppealDto create(List<MultipartFile> files, AppealRequestDto request) throws IOException {

        var theme = themeRepository.findById(request.themeId).orElseThrow(()
                -> new ResourceNotFoundException(request.themeId));

        if(request.startDate != null && request.endDate != null)
        {
            if(request.startDate.after(request.endDate))
            throw new NotRightsException("Incorrect date");
        }


        /*переделать и создать сущность с кодами*/
        
//        if( request.tradeCode != null)
//        {
//            if(request.tradeCode.length() != 10 && request.tradeCode.matches("[0-9]+"))
//                throw new NotRightsException("Incorrect tradeCode");
//        }


        var appeal = new Appeal();
        appeal.setCreateDate(new Date());
        appeal.setTheme(theme);
        appeal.setStartDate(request.startDate);
        appeal.setEndDate(request.endDate);
        appeal.setTradeCode(request.tradeCode);
        appeal.setAmount(request.amount);
        appeal.setEmail(request.email);
        appeal.setNameClient(request.clientName);
        appeal.setDescription(request.description);
        appeal.setStatusAppeal(StatusAppeal.NotProcessed);
        appeal.setClientId(request.clientId);

        appealRepository.save(appeal);

        if (files != null && !files.isEmpty()) {

            for (MultipartFile f :
                    files) {
                fileServiceImpl.store(f, appeal.getId(), appeal.getClientId());
            }


            return new AppealDto(appeal, fileServiceImpl.getFilesByAppealId(appeal.getId()));
        }
        return new AppealDto(appeal, null);
    }

    @Override
    public void delete(long id) {
        var appeal = appealRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException(id));

        /*check user*/
        appealRepository.delete(appeal);
    }


    /**
     * обновление обращения
     */
    @Override
    public AppealDto updateMyAppeal(long clientId, long id, AppealRequestDto request) {

        var appeal = appealRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException(id));

        var theme = themeRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException(id));

        if (appeal.getClientId() != clientId)
            throw new NotRightsException("This appeal you are not available");

        if(appeal.getStatusAppeal() != StatusAppeal.NeedUpdate || appeal.getStatusAppeal() != StatusAppeal.NotProcessed )
            throw new NotRightsException("This appeal cannot be updated because it is already being considered");

        if(request.startDate != null && request.endDate != null)
        {
            if(request.startDate.after(request.endDate))
                throw new NotRightsException("Incorrect date");
        }

        appeal.setTheme(theme);
        appeal.setAmount(request.amount);
        appeal.setTradeCode(request.tradeCode);
        appeal.setStartDate(request.startDate);
        appeal.setEndDate(request.endDate);
        appeal.setUpdateDate(new Date());
        appeal.setDescription(request.description);

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
    public List<AppealDto> myAppeals(long clientId) {

        return appealRepository
                .findByClientId(clientId, Sort.by(Sort.Direction.DESC, "createDate") )
                .stream()
                .map(x -> new AppealDto(x, fileServiceImpl.getFilesByAppealId(x.getId())))
                .collect(Collectors.toList());
    }
}
