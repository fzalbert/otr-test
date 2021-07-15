package com.example.appealsservice.domain.enums;

public enum StatusAppeal {
    NOTPROCCESING(0),
    INPROCCESING(1),
    SUCCESS(2),
    REJECT(3);
    private final int value;

    StatusAppeal(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
