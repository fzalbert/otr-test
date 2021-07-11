package com.example.employeesservice.repository;

import com.example.employeesservice.domain.Person;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Scope("prototype")
public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByLoginAndPassword(String login, String password);
}
