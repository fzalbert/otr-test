package com.example.employeesservice.repository;

import com.example.employeesservice.domain.Right;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;

@Scope("prototype")
public interface RightRepository extends JpaRepository<Right, Long> {
}
