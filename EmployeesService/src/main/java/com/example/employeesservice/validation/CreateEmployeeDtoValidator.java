package com.example.employeesservice.validation;

import com.example.employeesservice.common.validation.BaseValidator;
import com.example.employeesservice.dto.request.CreateEmployeeDto;
import com.example.employeesservice.exception.TemplateException;
import com.example.employeesservice.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateEmployeeDtoValidator implements BaseValidator<CreateEmployeeDto> {

    private static final String FIELD_EMAIL = "Данный email уже существует в базе";
    private static final String FIELD_LOGIN = "Данный Логин уже существует в базе";

    private final EmployeeRepository employeeRepository;

    @Autowired
    public CreateEmployeeDtoValidator(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void validate(CreateEmployeeDto obj) {
        validateUniqueEmail(obj);
    }


    private void validateUniqueEmail(CreateEmployeeDto createEmployeeDTO) {

        var checkLogin = this.employeeRepository
                .findAll()
                .stream()
                .anyMatch(x -> x.getPerson().getLogin().equals(createEmployeeDTO.getLogin()));

        if(checkLogin){
            throw new TemplateException((FIELD_LOGIN));
        }

        var employee = employeeRepository
                .findByEmail(createEmployeeDTO.getEmail())
                .orElse(null);

        if (employee != null)
            throw new TemplateException(FIELD_EMAIL);
    }
}
