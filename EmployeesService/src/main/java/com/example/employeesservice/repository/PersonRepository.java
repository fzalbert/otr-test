package com.example.employeesservice.repository;

import com.example.employeesservice.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
