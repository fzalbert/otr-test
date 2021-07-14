package org.example.dto.appeal;

public enum StatusAppeal {
    NOTPROCCESING(0),
    INPROCCESING(1),
    SUCCESS(2),
    REJECT(3);

    private final int value;

    StatusAppeal(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }

    public static StatusAppeal fromInt(int value){
        switch (value){
            case 1:
                return INPROCCESING;
            case 2:
                return SUCCESS;
            case 3:
                return REJECT;
            default:
                return NOTPROCCESING;
        }
    }
}

