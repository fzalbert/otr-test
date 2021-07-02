package com.example.appealsservice.repository;

import com.example.appealsservice.domain.Theme;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThemeRepository  extends JpaRepository<Theme, Long> {
}
