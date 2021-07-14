package com.example.appealsservice.service.impl;

import com.example.appealsservice.domain.Appeal;
import com.example.appealsservice.domain.Task;
import com.example.appealsservice.domain.enums.StatusAppeal;
import com.example.appealsservice.domain.enums.TaskStatus;
import com.example.appealsservice.dto.request.AppealRequestDto;
import com.example.appealsservice.dto.request.FilterAppealDto;
import com.example.appealsservice.dto.response.AppealDto;

import com.example.appealsservice.dto.response.ReportDto;
import com.example.appealsservice.dto.response.ShortAppealDto;
import com.example.appealsservice.exception.NotRightsException;
import com.example.appealsservice.exception.ResourceNotFoundException;
import com.example.appealsservice.httpModel.UserModel;
import com.example.appealsservice.kafka.model.MessageType;
import com.example.appealsservice.kafka.model.ModelConvertor;
import com.example.appealsservice.kafka.model.ModelMessage;
import com.example.appealsservice.messaging.MessageSender;
import com.example.appealsservice.repository.*;
import com.example.appealsservice.service.AppealService;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Scope("prototype")
@Service
public class AppealServiceImpl implements AppealService {

    private final AppealRepository appealRepository;
    private final TNVEDRepository tnvedRepository;
    private final FileServiceImpl fileServiceImpl;
    private final ThemeRepository themeRepository;
    private final ReportRepository reportRepository;
    private final TaskRepository taskRepository;
    private final CostCatRepository costCatRepository;

    private final MessageSender msgSender;

    public AppealServiceImpl(AppealRepository appealRepository, ThemeRepository themeRepository,
                             FileRepository fileRepository, FileServiceImpl fileServiceImpl,
                             TNVEDRepository tnvedRepository, MessageSender msgSender,
                             ReportRepository reportRepository, CostCatRepository costCatRepository,
                             TaskRepository taskRepository) {

        this.appealRepository = appealRepository;
        this.themeRepository = themeRepository;
        this.tnvedRepository = tnvedRepository;
        this.fileServiceImpl = fileServiceImpl;
        this.costCatRepository = costCatRepository;
        this.msgSender = msgSender;
        this.taskRepository = taskRepository;
        this.reportRepository = reportRepository;
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

        var report = reportRepository
                .findByAppealId(appeal.getId());


        return new AppealDto(appeal, files, new ReportDto(report));
    }

    /**
     * создание обращения
     */
    @Override
    public AppealDto create(List<MultipartFile> files, UserModel client, AppealRequestDto request) throws IOException {

        var theme = themeRepository.findById(request.themeId).orElseThrow(()
                -> new ResourceNotFoundException(request.themeId));

        var appeal = new Appeal();
        appeal.setTheme(theme);

        if(request.catCostId != null)
        {
            var costCat = costCatRepository.findById(request.catCostId).orElseThrow(()
                -> new ResourceNotFoundException(request.tnvedId));
            appeal.setCostCat(costCat);
        }

        if(request.endDate != null)
        {
            var date = new Date();
            if(!date.after(request.endDate))
            throw new NotRightsException("Incorrect date");
        }

        if(request.tnvedId != null)
        {
           var tnved = tnvedRepository.findById(request.tnvedId).orElseThrow(()
                    -> new ResourceNotFoundException(request.tnvedId));
            appeal.setTnved(tnved);
        }
        appeal.setCreateDate(new Date());
        appeal.setAmount(request.amount);
        appeal.setEmail(client.getEmail());
        appeal.setNameOrg(client.getName());
        appeal.setDescription(request.description);
        appeal.setStatusAppeal(StatusAppeal.NOTPROCCESING);
        appeal.setClientId(client.getId());


        appealRepository.save(appeal);
        if (files != null && !files.isEmpty()) {

            for (MultipartFile f :
                    files) {
                fileServiceImpl.store(f, appeal.getId(), appeal.getClientId());
            }
        }


        ModelMessage model = ModelConvertor.Convert(appeal.getEmail(),
                    appeal.getNameOrg(), "APPEAL SUCCESSFULLY CREATED",appeal.getId().toString(), MessageType.APPEALCREATE);
        msgSender.send(model);

        var task = new Task();
        task.setOver(false);
        task.setTaskStatus(TaskStatus.NEEDCHECK);
        task.setAppeal(appeal);
        task.setDate(new Date());

        taskRepository.save(task);

        return new AppealDto(appeal, fileServiceImpl.getFilesByAppealId(appeal.getId()), null);
    }


    /**
     * удаление обращения
     */
    @Override
    public void deleteById(Long id) {
        var appeal = appealRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException(id));

        appealRepository.delete(appeal);
    }


    /**
     * обновление обращения клиентом(до того как возьмет сотрудник)
     */
    @Override
    public AppealDto updateMyAppeal(Long clientId, Long id, AppealRequestDto request){

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


        if(request.endDate != null)
        {
            var date = new Date();
            if(!date.after(request.endDate))
                throw new NotRightsException("Incorrect date");

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

        appealRepository.save(appeal);


        return new AppealDto(appeal, fileServiceImpl.getFilesByAppealId(appeal.getId()), null);

    }

    /**
     * обновление обращения сотрудником
     */
    @Override
    public AppealDto update(Long employeeId, Long id, AppealRequestDto request) {

        var appeal = appealRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException(id));

        if(appeal.isOver())
            throw new NotRightsException("Appeal is over");

        var task = taskRepository
                .getByAppealId(id)
                .stream()
                .sorted(Comparator.comparing(Task::getDate, Comparator.reverseOrder()))
                .findFirst()
                .get();

        if(task.getTaskStatus() != TaskStatus.NEEDCHECK)
            throw new NotRightsException("Task not update");

        if(task.getEmployeeId() != null)
            throw new NotRightsException("Task already busy");


        if(request.tnvedId != null)
        {
            var theme = themeRepository.findById(request.themeId).orElseThrow(()
                    -> new ResourceNotFoundException(request.themeId));
            appeal.setTheme(theme);
        }


        if(request.endDate != null)
        {
            var date = new Date();
            if(!date.after(request.endDate))
                throw new NotRightsException("Incorrect date");

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

        appealRepository.save(appeal);

        task.setEmployeeId(employeeId);

        var newTask = new Task();
        newTask.setTaskStatus(TaskStatus.NEEDCHECK);
        newTask.setAppeal(appeal);
        newTask.setDate(new Date());

        taskRepository.save(newTask);

        ModelMessage model = ModelConvertor.Convert(appeal.getEmail(),
                appeal.getNameOrg(), "APPEAL IS UPDATE", appeal.getId().toString(), MessageType.UPDATE);

        //msgSender.send(model);

        return new AppealDto(appeal, fileServiceImpl.getFilesByAppealId(appeal.getId()), null);

    }

    /**
     * получение списка обращений с помощью фильтра
     */
    @Override
    public List<ShortAppealDto> filter(FilterAppealDto filter) {

        var appeals =  appealRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Appeal::getCreateDate, Comparator.reverseOrder()))
                .collect(Collectors.toList());

        if (filter != null && filter.themeId != null )
            appeals = appeals.stream().filter(x -> x.getTheme().getId() == (filter.themeId))
                    .collect(Collectors.toList());


        if (filter != null && filter.statusAppeal != null)
            appeals = appeals.stream().filter(x -> x.getStatusAppeal() == filter.statusAppeal)
                    .collect(Collectors.toList());

        if (filter != null && filter.date != null)
            appeals = appeals.stream().filter(x -> x.getCreateDate().after(filter.date))
                    .collect(Collectors.toList());

        return appeals.stream().map(ShortAppealDto::new)
                .collect(Collectors.toList());
    }

    /**
     * получение списка обращений клиента
     */
    @Override
    public List<ShortAppealDto> myAppeals(Long clientId) {

        return appealRepository
                .findByClientId(clientId, Sort.by(Sort.Direction.DESC, "createDate") )
                .stream()
                .map(ShortAppealDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public void check(Long id, TaskStatus status) {
        var appeal = appealRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        var task = taskRepository.findByAppealIdAndTaskStatus(appeal.getId(), TaskStatus.NEEDCHECK)
                .orElseThrow(() -> new ResourceNotFoundException(appeal.getId()));

        task.setOver(true);
        taskRepository.save(task);

        var newTask = new Task();
        newTask.setTaskStatus(status);
        newTask.setAppeal(appeal);
        newTask.setDate(new Date());

        taskRepository.save(newTask);
    }
}
