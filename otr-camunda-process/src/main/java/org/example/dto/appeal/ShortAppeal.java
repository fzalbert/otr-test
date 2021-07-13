package org.example.dto.appeal;

import lombok.Getter;
import lombok.Setter;
import org.example.dto.Theme;

import java.util.Date;

@Getter
@Setter
public class ShortAppeal {

    private Long id;

    private Long clientId;

    private Theme theme;

    private String description;

    private Date createDate;

    private StatusAppeal statusAppeal;


    public ShortAppeal() {}

    public ShortAppeal(Appeal appeal) {

        if(appeal == null)
            return;
        id = appeal.getId();
        clientId = appeal.getClientId();
        theme = appeal.getTheme();
        description = appeal.getDescription();
        createDate = appeal.getCreateDate();
        statusAppeal = appeal.getStatusAppeal();
    }
}
