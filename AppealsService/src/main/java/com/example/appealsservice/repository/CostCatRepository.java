package com.example.appealsservice.repository;

import com.example.appealsservice.domain.CostCat;
import com.example.appealsservice.domain.TNVED;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CostCatRepository extends JpaRepository<CostCat, Long> {
}
