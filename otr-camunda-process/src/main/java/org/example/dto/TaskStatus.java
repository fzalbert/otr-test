package org.example.dto;

public enum TaskStatus {

    NEEDCHECK(0),
    NEEDUPDATE(1),
    NEEDREJECT(2),
    NEEDSUCCESS(3);

    private final int value;

    TaskStatus(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }
}
