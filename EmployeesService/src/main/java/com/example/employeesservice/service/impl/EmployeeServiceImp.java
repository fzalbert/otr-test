package com.example.employeesservice.service.impl;

import com.example.employeesservice.domain.Employee;
import com.example.employeesservice.domain.Person;
import com.example.employeesservice.domain.enums.RoleType;
import com.example.employeesservice.dto.request.AuthDto;
import com.example.employeesservice.dto.request.CreateEmployeeDto;
import com.example.employeesservice.dto.request.UpdateEmployeeDto;
import com.example.employeesservice.dto.response.EmployeeDto;
import com.example.employeesservice.dto.response.EmployeeModelDto;
import com.example.employeesservice.exception.TemplateException;
import com.example.employeesservice.kafka.EmployeeModel;
import com.example.employeesservice.kafka.KafkaSender;
import com.example.employeesservice.repository.EmployeeRepository;
import com.example.employeesservice.repository.PersonRepository;
import com.example.employeesservice.repository.RoleRepository;
import com.example.employeesservice.service.EmployeeService;
import com.example.employeesservice.utils.CryptoHelper;
import com.example.employeesservice.validation.CreateEmployeeDtoValidator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Scope("prototype")
public class EmployeeServiceImp implements EmployeeService {

    @Value("${secret}")
    private String secretKey;
    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;
    private final CreateEmployeeDtoValidator dtoValidator;
    private final PersonRepository personRepository;
    private final KafkaSender kafkaSender;

    @Autowired
    public EmployeeServiceImp(EmployeeRepository employeeRepository, PersonRepository personRepository,
                              RoleRepository roleRepository, CreateEmployeeDtoValidator dtoValidator,
                              KafkaSender kafkaSender) {
        this.employeeRepository = employeeRepository;
        this.roleRepository = roleRepository;
        this.dtoValidator = dtoValidator;
        this.personRepository = personRepository;
        this.kafkaSender = kafkaSender;
    }

    /**
     * Авторизация
     */
    @Override
    public EmployeeModelDto auth(AuthDto request) {
        CryptoHelper.setSecretKey(secretKey);
        var hmacPassword = CryptoHelper.HMAC(request.getPassword());

        var user = personRepository
                .findByLoginAndPassword(request.getLogin(), hmacPassword)
                .orElseThrow(() -> new TemplateException("Пользователь не найден"));

        if (!user.isActive())
            throw new TemplateException("Вас заблокировали");

        var employee = user.getEmployee();
        if(employee == null)
            throw new TemplateException("Сотрудник не найден");

        return new EmployeeModelDto(employee.getId(),
                employee.getEmail(),
                employee.getLastName(),
                employee.getRoleType().name());
    }

    /**
     * Создание сотрудника
     */
    @Override
    public boolean create(CreateEmployeeDto request) {
        dtoValidator.validate(request);
        var employee = this.dtoToEntity(request, new Employee());

        employeeRepository.save(employee);
        kafkaSender.sendEmployee(new EmployeeModel(employee));
        return true;
    }

    /**
     * Обновление сотрудника
     */
    @Override
    public EmployeeDto update(UpdateEmployeeDto request, long employeeId) {
        var employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new TemplateException("Сотрудник с таким id не найден"));

        var updatedEmployee = this.dtoToEntity(request, employee);
        employeeRepository.save(updatedEmployee);

        kafkaSender.sendUpdateEmployee(new EmployeeModel(updatedEmployee));
        return new EmployeeDto(updatedEmployee);
    }

    /**
     * Удаление сотрудника
     */
    @Override
    public boolean delete(long employeeId) {
        var employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new TemplateException("Сотрудник с таким id не найден"));
        employeeRepository.delete(employee);
        return true;
    }

    /**
     * Получить список всех сотрудников в системе
     */
    @Override
    public List<EmployeeDto> getList() {
        return employeeRepository
                .findAll(Sort.by(Sort.Direction.ASC, "id"))
                .stream()
                .map(EmployeeDto::new)
                .collect(Collectors.toList());
    }

    /**
     * Получить данные о сотруднике по id
     */
    @Override
    public EmployeeDto getById(long employeeId) {
        var employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new TemplateException("Сотрудник с таким id не найден"));
        return new EmployeeDto(employee);
    }

    /**
     * Назначить роль сотруднику
     */
    @Override
    public boolean appointRole(long employeeId, RoleType role) {
        var employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new TemplateException("Сотрудник с таким id не найден"));

        employee.setRoleType(role);
        employeeRepository.save(employee);
        kafkaSender.sendUpdateEmployee(new EmployeeModel(employee));
        return true;
    }

    /**
     * Разблокировать сотрудник по id
     */
    @Override
    public void blockById(long id) {
        this.changeActiveEmployee(false, id);
    }

    /**
     * Разблокировать сотрудник по id
     */
    @Override
    public void unblockById(long id) {
        this.changeActiveEmployee(true, id);
    }

    /**
     * Изменить статус сотрудника
     */
    private void changeActiveEmployee(boolean isActive, long id) {
        var client = employeeRepository
                .findById(id)
                .orElseThrow(() -> new TemplateException("Сотрудник с таким id не найден"));

        var person = personRepository
                .findById(client.getPerson().getId())
                .orElseThrow(() -> new TemplateException("Пользователь не найден"));

        person.setActive(isActive);
        personRepository.save(person);
    }

    /**
     * Преобразовать из dto в сущность employee
     */
    private Employee dtoToEntity(CreateEmployeeDto request, Employee employee) {
        CryptoHelper.setSecretKey(secretKey);
        BeanUtils.copyProperties(request, employee);

        var person = new Person();
        BeanUtils.copyProperties(request, person, "password");
        person.setPassword(CryptoHelper.HMAC(request.getPassword()));
        person.setRegistrationDate(new Date());
        person.setActive(true);
        employee.setPerson(person);

        return employee;
    }

    /**
     * Преобразовать из dto в сущность employee
     */
    private Employee dtoToEntity(UpdateEmployeeDto request, Employee employee) {
        CryptoHelper.setSecretKey(secretKey);
        BeanUtils.copyProperties(request, employee);
        return employee;
    }
}
