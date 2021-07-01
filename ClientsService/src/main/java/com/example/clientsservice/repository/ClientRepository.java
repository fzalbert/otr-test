package com.example.clientsservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import  com.example.clientsservice.domain.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
