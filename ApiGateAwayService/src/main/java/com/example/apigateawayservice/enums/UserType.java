package com.example.apigateawayservice.enums;

public enum UserType {
    //TODO Enum не полный и не пригодный к полноценному использованию(показал желательное использование)
    SUPER_ADMIN(0, "Суперадмин") {
        @Override
        public String stringValue() {
            return String.format("У %s в перечислении номер %s", getTitle(), getNumber());
        }
    },
    ADMIN(1, "Администратор") {
        @Override
        public String stringValue() {
            return String.format("У %s в перечислении номер %s", getTitle(), getNumber());
        }
    },
    EMPLOYEE(2, "Оператор") {
        @Override
        public String stringValue() {
            return String.format("У %s в перечислении номер %s", getTitle(), getNumber());
        }
    },
    CLIENT(3, "Клиент") {
        @Override
        public String stringValue() {
            return String.format("У %s в перечислении номер %s", getTitle(), getNumber());
        }
    };

    private final int number;
    private final String title;

    UserType(int number, String title){
        this.number = number;
        this.title = title;
    }

    public int getNumber(){ return number;}

    public String getTitle() { return title; }

    public abstract String stringValue();
}
