package com.example.appealsservice.repository;

import com.example.appealsservice.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
