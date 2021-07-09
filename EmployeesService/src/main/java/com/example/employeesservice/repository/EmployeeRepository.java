package com.example.employeesservice.repository;

import com.example.employeesservice.domain.Employee;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Scope("prototype")
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmail(String email);
}
