package com.example.employeesservice.service.impl;

import com.example.employeesservice.domain.Employee;
import com.example.employeesservice.domain.Person;
import com.example.employeesservice.domain.enums.RoleType;
import com.example.employeesservice.dto.request.AuthDto;
import com.example.employeesservice.dto.request.CreateEmployeeDTO;
import com.example.employeesservice.dto.request.UpdateEmployeeDto;
import com.example.employeesservice.dto.response.EmployeeDTO;
import com.example.employeesservice.dto.response.EmployeeModelDTO;
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

    /** Авторизация */
    @Override
    public EmployeeModelDTO auth(AuthDto request) {
        CryptoHelper.setSecretKey(secretKey);
        var hmacPassword = CryptoHelper.HMAC(request.getPassword());

        var user = personRepository
                .findByLoginAndPassword(request.getLogin(), hmacPassword)
                .orElseThrow(TemplateException::new);

        var employee = user.getEmployee();

        return new EmployeeModelDTO(employee.getId(),
                employee.getEmail(),
                employee.getLastName(),
                employee.getRoleType().name());
    }

    /** Создание сотрудника */
    @Override
    public boolean create(CreateEmployeeDTO request) {
        dtoValidator.validate(request);
        var employee = this.dtoToEntity(request, new Employee());

        employeeRepository.save(employee);

        var model = new EmployeeModel();
        model.setEmail(request.getEmail());
        model.setId(employee.getId());
        model.setPassword(request.getPassword());
        model.setName(request.getFirstName());
        model.setLogin(request.getLogin());
        model.setLastName(request.getLastName());
        model.setRole(request.getRoleType());
        kafkaSender.sendEmployee(model);

        return true;
    }

    /** Обновление сотрудника */
    @Override
    public EmployeeDTO update(UpdateEmployeeDto request, long employeeId) {
        var employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new TemplateException("Сотрудник с таким id не найден"));

        var updatedEmployee = this.dtoToEntity(request, employee);
        employeeRepository.save(updatedEmployee);
        return new EmployeeDTO(updatedEmployee);
    }

    /** Удаление сотрудника */
    @Override
    public boolean delete(long employeeId) {
        var employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new TemplateException("Сотрудник с таким id не найден"));
        employeeRepository.delete(employee);
        return true;
    }

    /** Получить список всех сотрудников в системе */
    //@Cacheable(value = "itemCache")
    @Override
    public List<EmployeeDTO> getList() {
        return employeeRepository
                .findAll()
                .stream()
                .sorted(Comparator.comparing(Employee::getLastName, Comparator.reverseOrder()))
                .map(EmployeeDTO::new)
                .collect(Collectors.toList());
    }

    /** Получить данные о сотруднике по id */
    @Override
    public EmployeeDTO getById(long employeeId) {
        var employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new TemplateException("Сотрудник с таким id не найден"));
        return new EmployeeDTO(employee);
    }

    /** Назначить роль сотруднику */
    @Override
    public boolean appointRole(long employeeId, RoleType role) {
        var employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new TemplateException("Сотрудник с таким id не найден"));

        employee.setRoleType(role);
        employeeRepository.save(employee);
        return true;
    }

    /** Преобразовать из dto в сущность employee */
    private Employee dtoToEntity(CreateEmployeeDTO request, Employee employee) {
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

    /** Преобразовать из dto в сущность employee */
    private Employee dtoToEntity(UpdateEmployeeDto request, Employee employee) {
        CryptoHelper.setSecretKey(secretKey);
        BeanUtils.copyProperties(request, employee);

        var person = new Person();
        BeanUtils.copyProperties(request, person, "password");
        if (request.getPassword() != null)
            person.setPassword(CryptoHelper.HMAC(request.getPassword()));
        person.setRegistrationDate(new Date());
        person.setActive(true);
        employee.setPerson(person);

        return employee;
    }

    @Override
    public void blockById(long id) {

        var client = employeeRepository.findById(id).orElseThrow(() -> new TemplateException("Сотрудник с таким id не найден"));

        var person = personRepository
                .findById(client.getPerson().getId())
                .get();

        person.setActive(false);

        personRepository.save(person);
    }

    /**
     * Разблокировать клиента по id
     */
    @Override
    public void unblockById(long id) {
        var client = employeeRepository.findById(id).orElseThrow(() -> new TemplateException("Сотрудник с таким id не найден"));

        var person = personRepository
                .findById(client.getPerson().getId())
                .get();

        person.setActive(true);

        personRepository.save(person);
    }
}
