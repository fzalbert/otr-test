package com.example.appealsservice.repository;

import com.example.appealsservice.domain.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {
}
