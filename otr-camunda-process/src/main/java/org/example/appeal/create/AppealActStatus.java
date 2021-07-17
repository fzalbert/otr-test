package org.example.appeal.create;

import org.example.dto.report.ReportStatus;

public class AppealActStatus {

    public static int Allow = 0;
    public static int Denied = 1;
    public static int Change = 2;

    public static int getStatus(boolean isAllow, boolean isDenied, boolean isChanged) throws Exception {
        if(isAllow)
            return 0;
        else if(isDenied)
            return 1;
        else if(isChanged)
            return 2;
        else throw new Exception("status not selected");
    }

    public static int getStatus(ReportStatus status){
        switch (status.name()){
            case "SUCCESS":
                return Allow;
            default:
                return Denied;
        }
    }
}
