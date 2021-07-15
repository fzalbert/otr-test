package com.example.employeesservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
//@EnableCaching
public class EmployeesServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmployeesServiceApplication.class, args);
    }

}
