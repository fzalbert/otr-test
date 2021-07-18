package org.example.dto.appeal;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.dto.Theme;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
public class Appeal {

    private Long id;

    private Long employeeId;

    private String nameOrg;

    private Theme theme;

    private String description;

    private Date createDate;

    private Integer statusAppeal;

    public Appeal(AppealStatusChangedDto request){
        if (request == null)
            return;
        BeanUtils.copyProperties(request, this);
    }

    public Map<String, Object> toVariableMap(){
        Map<String, Object> result = new HashMap<>();

        result.put("appeals_id", getId());
        result.put("appeal_client_name", nameOrg);
        result.put("appeal_status_text", StatusAppealParser.toString(StatusAppeal.fromInt(statusAppeal)));
        result.put("appeal_status", getStatusAppeal());
        result.put("created_at", getCreateDate());
        result.put("appeal_theme_name", getTheme().getName());
        result.put("appeal_theme", getTheme());

        return result;
    }
}