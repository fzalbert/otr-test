package com.example.employeesservice;

import com.example.employeesservice.domain.enums.RoleType;
import com.example.employeesservice.dto.request.CreateEmployeeDto;
import com.example.employeesservice.repository.EmployeeRepository;
import com.example.employeesservice.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;


@Component
public class InitialDataLoader implements ApplicationRunner {

    private final EmployeeService employeeService;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public InitialDataLoader(
            EmployeeService employeeService,
            EmployeeRepository employeeRepository
    ) {
        this.employeeService = employeeService;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        createSuperAdmin();
    }

    private void createSuperAdmin(){

        var isExists = employeeRepository.existsByRoleType(RoleType.SUPER_ADMIN);

        if(!isExists) {
            var admin = loadAdmin();
            if(admin == null)
                return;

            admin.setRoleType(RoleType.SUPER_ADMIN);
            employeeService.create(admin);
        }
    }

    private CreateEmployeeDto loadAdmin() {
        URL res = getClass().getClassLoader().getResource("super_admin.json");

        File file;
        try {
            file = Paths.get(res.toURI()).toFile();
        }
        catch (URISyntaxException | NullPointerException ex){
            ex.printStackTrace();
            return null;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(file, CreateEmployeeDto.class);
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
