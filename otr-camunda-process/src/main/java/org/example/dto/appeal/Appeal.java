package org.example.dto.appeal;

import lombok.Getter;
import lombok.Setter;
import org.example.dto.FileDto;
import org.example.dto.TNVED;
import org.example.dto.Theme;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Appeal {

    private Long id;

    private Long clientId;

    private Theme theme;

    private String description;

    private Date startDate;

    private Date endDate;

    private TNVED tnved;

    private double amount;

    private Date createDate;

    private StatusAppeal statusAppeal;

    private List<FileDto> files;

    public Map<String, Object> toVariableMap(){
        Map<String, Object> result = new HashMap<>();

        result.put("appeals_id", getId());
        result.put("appeal_client_name", getClientId());
        result.put("appeal_status_text", StatusAppealParser.toString(getStatusAppeal()));
        result.put("appeal_status", getStatusAppeal());
        result.put("created_at", getCreateDate());
        result.put("appeal_theme_name", getTheme().getName());
        result.put("appeal_theme", getTheme());

        return result;
    }
}