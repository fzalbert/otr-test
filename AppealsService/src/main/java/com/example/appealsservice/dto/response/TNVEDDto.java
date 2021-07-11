package com.example.appealsservice.dto.response;


import com.example.appealsservice.domain.TNVED;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TNVEDDto {
    public Long id;
    public String code;
    public String name;

    public TNVEDDto()
    {

    }
    public TNVEDDto(TNVED tnved)
    {
        if (tnved == null)
            return;
        id = tnved.getId();
        code = tnved.getCode();
        name = tnved.getName();
    }
    public TNVEDDto(Long id, String code, String name)
    {
        this.id = id;
        this.code = code;
        this.name = name;
    }
}
