package com.example.appealsservice.repository;


import com.example.appealsservice.domain.Appeal;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AppealRepository extends JpaRepository<Appeal, Long> {

    List<Appeal> findByClientId(Long clientId, Sort sort);

}
