package com.example.appealsservice.repository;


import com.example.appealsservice.domain.Appeal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppealRepository extends JpaRepository<Appeal, Long> {
}
