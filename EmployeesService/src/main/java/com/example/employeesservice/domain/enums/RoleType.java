package com.example.employeesservice.domain.enums;

public enum RoleType {
    SUPER_ADMIN(0),
    ADMIN(1),
    EMPLOYEE(2);
    private final int value;

    RoleType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
