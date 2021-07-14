package com.example.appealsservice.domain.enums;

public enum ReportStatus {
    SUCCESS(0),
    REJECTED(1);
    private final int value;

    ReportStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
