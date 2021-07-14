package com.example.appealsservice.repository;

import com.example.appealsservice.domain.Task;
import com.example.appealsservice.domain.enums.TaskStatus;
import com.example.appealsservice.dto.response.TaskDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> getByAppealId(long appealId);
    Optional<Task> findByAppealIdAndTaskStatus(long appealId, TaskStatus taskStatus);
}
