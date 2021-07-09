package com.example.employeesservice.repository;

import com.example.employeesservice.domain.Role;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;

@Scope("prototype")
public interface RoleRepository extends JpaRepository<Role, Long> {
}
