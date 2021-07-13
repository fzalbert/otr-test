package org.example.dto.appeal;

import org.example.dto.appeal.StatusAppeal;

public class StatusAppealParser {

    public static String toString(StatusAppeal status) {
        String text = "";
        switch (status) {
            case NOTPROCCESING:
                text = "Not Proccesing";
                break;
            case INPROCCESING:
                text = "INPROCCESING";
                break;
            case SUCCESS:
                text = "SUCCESS";
                break;
            case REJECT:
                text = "REJECT";
                break;
        }
        return text;
    }
}
