package com.example.appealsservice.repository;

import com.example.appealsservice.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
