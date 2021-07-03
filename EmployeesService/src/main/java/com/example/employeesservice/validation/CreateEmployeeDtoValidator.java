package com.example.employeesservice.validation;

import com.example.employeesservice.common.validation.BaseValidator;
import com.example.employeesservice.dto.request.CreateEmployeeDTO;
import com.example.employeesservice.exception.FieldNotUniqueException;
import com.example.employeesservice.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateEmployeeDtoValidator implements BaseValidator<CreateEmployeeDTO> {

    private static final String FIELD_EMAIL = "email";

    private final EmployeeRepository employeeRepository;

    @Autowired
    public CreateEmployeeDtoValidator(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void validate(CreateEmployeeDTO obj) {
        validateUniqueEmail(obj);
    }


    private void validateUniqueEmail(CreateEmployeeDTO createEmployeeDTO) {
        var employee = employeeRepository
                .findByEmail(createEmployeeDTO.getEmail())
                .orElse(null);

        if (employee != null)
            throw new FieldNotUniqueException(FIELD_EMAIL);
    }
}
