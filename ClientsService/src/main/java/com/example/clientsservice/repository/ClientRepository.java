package com.example.clientsservice.repository;

import com.example.clientsservice.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
