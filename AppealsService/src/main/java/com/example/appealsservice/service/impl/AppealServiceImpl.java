package com.example.appealsservice.service.impl;

import com.example.appealsservice.domain.Appeal;
import com.example.appealsservice.domain.Task;
import com.example.appealsservice.domain.enums.StatusAppeal;
import com.example.appealsservice.domain.enums.TaskStatus;
import com.example.appealsservice.dto.request.AppealRequestDto;
import com.example.appealsservice.dto.request.FilterAppealAdminDto;
import com.example.appealsservice.dto.request.FilterAppealDto;
import com.example.appealsservice.dto.response.*;

import com.example.appealsservice.exception.NotRightsException;
import com.example.appealsservice.exception.ResourceNotFoundException;
import com.example.appealsservice.exception.TemplateException;
import com.example.appealsservice.httpModel.UserModel;
import com.example.appealsservice.kafka.model.MessageType;
import com.example.appealsservice.kafka.model.ModelConvertor;
import com.example.appealsservice.kafka.model.ModelMessage;
import com.example.appealsservice.messaging.MessageSender;
import com.example.appealsservice.repository.*;
import com.example.appealsservice.service.AppealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
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
    private final ReportRepository reportRepository;
    private final TaskRepository taskRepository;
    private final CostCatRepository costCatRepository;

    private final MessageSender msgSender;

    @Autowired
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
                -> new TemplateException("Обращение не найдено"));

        var files = fileServiceImpl.getFilesByAppealId(id);

        var report = reportRepository
                .findByAppealId(appeal.getId());

        var task = taskRepository
                .getByAppealIdAndIsOverFalse(appeal.getId())
                .stream()
                .findFirst()
                .orElseThrow(() -> new TemplateException("Задача не взята в работу"));


        return new AppealDto(appeal, files, new ReportDto(report), new TaskDto(task));
    }

    /**
     * создание обращения
     */
    @Override
    public AppealDto create(List<MultipartFile> files, UserModel client, AppealRequestDto request) throws IOException {
        var theme = themeRepository.findById(request.themeId).orElseThrow(()
                -> new TemplateException("Тема не найдена"));

        var appeal = new Appeal();
        appeal.setTheme(theme);

        if (request.catCostId != null) {
            var costCat = costCatRepository.findById(request.catCostId).orElseThrow(()
                    -> new TemplateException("Неверные данные по категориям затрат"));
            appeal.setCostCat(costCat);
        }

        if (request.endDate != null) {
            var date = new Date();
            if (date.after(request.endDate))
                throw new TemplateException("Неверная дата окончания");
        }

        if (request.tnvedId != null) {
            var tnved = tnvedRepository.findById(request.tnvedId).orElseThrow(()
                    -> new TemplateException("Неверный код ТН ВЭД"));
            appeal.setTnved(tnved);
        }
        if (request.amount != null)
            appeal.setAmount(request.amount);

        appeal.setCreateDate(new Date());
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

        var task = new Task();
        task.setOver(false);
        task.setTaskStatus(TaskStatus.NEEDCHECK);
        task.setAppeal(appeal);
        task.setDate(new Date());

        taskRepository.save(task);

        ModelMessage model = ModelConvertor.Convert(appeal.getEmail(),
                appeal.getNameOrg(), appeal.getId().toString(), "APPEAL SUCCESSFULLY CREATED", MessageType.APPEALCREATE);
        msgSender.sendEmail(model);

        msgSender.sendAppeal(new ShortAppealDto(appeal));

        return new AppealDto(appeal, fileServiceImpl.getFilesByAppealId(appeal.getId()), null, new TaskDto(task));
    }


    /**
     * удаление обращения
     */
    @Override
    public void deleteById(Long id) {
        var appeal = appealRepository.findById(id).orElseThrow(()
                -> new TemplateException("Обращение не найдено"));

        appealRepository.delete(appeal);
    }


    /**
     * обновление обращения клиентом(до того как возьмет сотрудник)
     */
    @Override
    public AppealDto updateMyAppeal(Long clientId, Long id, AppealRequestDto request) {
        var appeal = appealRepository.findById(id).orElseThrow(()
                -> new TemplateException("Обращение не найдено"));

        if (appeal.getClientId() != clientId)
            throw new TemplateException("Обращение закреплено за вами");

        if (appeal.getStatusAppeal() != StatusAppeal.NOTPROCCESING)
            throw new TemplateException("Это обращение уже начали рассматривать");

        if (request.tnvedId != null) {
            var theme = themeRepository.findById(request.themeId).orElseThrow(()
                    -> new TemplateException("Тема не найдена"));
            appeal.setTheme(theme);
        }


        if (request.endDate != null) {
            var date = new Date();
            if (date.after(request.endDate))
                throw new TemplateException("Неверная дата окончания");

            appeal.setEndDate(request.endDate);
        }

        if (request.tnvedId != null && request.tnvedId > 0) {
            var tnved = tnvedRepository.findById(request.tnvedId).orElseThrow(()
                    -> new TemplateException("Неверный код ТН ВЭД"));
            appeal.setTnved(tnved);
        }

        appeal.setAmount(request.amount);

        appeal.setUpdateDate(new Date());
        appeal.setDescription(request.description);

        appealRepository.save(appeal);


        return new AppealDto(appeal, fileServiceImpl.getFilesByAppealId(appeal.getId()), null, null);
    }

    /**
     * обновление обращения сотрудником
     */
    @Override
    public AppealDto update(Long employeeId, Long id, AppealRequestDto request) {

        var appeal = appealRepository.findById(id).orElseThrow(()
                -> new TemplateException("Обращение не найдено"));

        if (appeal.isOver())
            throw new TemplateException("Задача завершена");

        var task = taskRepository
                .getByAppealId(id)
                .stream()
                .sorted(Comparator.comparing(Task::getDate, Comparator.reverseOrder()))
                .findFirst()
                .orElse(null);

        if (task.getTaskStatus() != TaskStatus.NEEDUPDATE)
            throw new TemplateException("Задача в неверном статусе");

        if (!task.getEmployeeId().equals(employeeId))
            throw new TemplateException("Задача уже закреплена за исполнителем");


        if (request.tnvedId != null) {
            var theme = themeRepository.findById(request.themeId).orElseThrow(()
                    -> new TemplateException("Тема не найдена"));
            appeal.setTheme(theme);
        }


        if (request.endDate != null) {
            var date = new Date();
            if (date.after(request.endDate))
                throw new TemplateException("Неверная дата окончания");

            appeal.setEndDate(request.endDate);
        }

        if (request.tnvedId != null && request.tnvedId > 0) {
            var tnved = tnvedRepository.findById(request.tnvedId).orElseThrow(()
                    -> new TemplateException("Неверный код ТН ВЭД"));
            appeal.setTnved(tnved);
        }

        appeal.setAmount(request.amount);

        appeal.setUpdateDate(new Date());
        appeal.setDescription(request.description);

        appealRepository.save(appeal);
        task.setOver(true);
        taskRepository.save(task);

        task.setEmployeeId(employeeId);

        var newTask = new Task();
        newTask.setTaskStatus(TaskStatus.NEEDCHECK);
        newTask.setAppeal(appeal);
        newTask.setDate(new Date());

        taskRepository.save(newTask);

        ModelMessage model = ModelConvertor.Convert(appeal.getEmail(),
                appeal.getNameOrg(), "APPEAL IS UPDATE", appeal.getId().toString(), MessageType.UPDATE);

        msgSender.sendEmail(model);
        msgSender.sendAppeal(new ShortAppealDto(appeal));

        return new AppealDto(appeal, fileServiceImpl.getFilesByAppealId(appeal.getId()), null, null);
    }

    /**
     * получение списка обращений с помощью фильтра
     */
    @Override
    public List<ShortAppealDto> filter(Long clientId, FilterAppealDto filter) {
        var appeals = appealRepository
                .findByClientId(clientId, Sort.by(Sort.Direction.DESC, "createDate"))
                .stream()
                .collect(Collectors.toList());

        if (filter != null && filter.themeId != null)
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
                .findByClientId(clientId, Sort.by(Sort.Direction.DESC, "createDate"))
                .stream()
                .map(ShortAppealDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public void check(Long id, TaskStatus status) {
        var appeal = appealRepository.findById(id)
                .orElseThrow(() -> new TemplateException("Обращние не найдено"));

        var task = taskRepository.findByAppealIdAndTaskStatusAndIsOverFalse(appeal.getId(), TaskStatus.NEEDCHECK)
                .orElseThrow(() -> new TemplateException("Задача не найдена"));

        task.setOver(true);
        taskRepository.save(task);

        var newTask = new Task();
        newTask.setTaskStatus(status);
        newTask.setAppeal(appeal);
        newTask.setDate(new Date());


        msgSender.sendChangeStatus(new AppealStatusChangedDto(new ShortAppealDto(appeal), status));
        taskRepository.save(newTask);
    }

    /**
     * получение списка обращений по фильтрам для админа
     */
    @Override
    public List<ShortAppealDto> filterAdmin(FilterAppealAdminDto filter) {
        var appeals = appealRepository
                .findAll(Sort.by(Sort.Direction.DESC, "createDate"))
                .stream()
                .collect(Collectors.toList());

        if (filter != null && filter.themeId != null)
            appeals = appeals.stream().filter(x -> x.getTheme().getId() == (filter.themeId))
                    .collect(Collectors.toList());

        if (filter != null && filter.statusAppeal != null)
            appeals = appeals.stream().filter(x -> x.getStatusAppeal() == filter.statusAppeal)
                    .collect(Collectors.toList());

        if (filter != null && filter.date != null)
            appeals = appeals.stream().filter(x -> x.getCreateDate().after(filter.date))
                    .collect(Collectors.toList());

        if (filter != null && filter.employeeId != null) {
            appeals = appeals.stream().filter(x -> taskRepository.findByAppealIdAndIsOverFalse(x.getId()) != null)
                    .collect(Collectors.toList());
        }

        if (filter != null && filter.nameOrg != null)
            appeals = appeals.stream().filter(x -> x.getNameOrg().contains(filter.nameOrg))
                    .collect(Collectors.toList());

        return appeals.stream().map(x -> new ShortAppealDto(x, taskRepository.findByAppealIdAndIsOverFalse(x.getId()).getEmployeeId()))
                .collect(Collectors.toList());
    }
}
