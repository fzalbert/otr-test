package com.example.appealsservice.service;

public interface ReportService {

    void approve(long taskId, String text);
    void reject(long taskId, String text);
}
