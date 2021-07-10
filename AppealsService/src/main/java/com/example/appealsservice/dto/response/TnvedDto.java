package com.example.appealsservice.dto.response;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TnvedDto {
    public Integer id;
    public String code;
    public String name;

    public TnvedDto()
    {

    }
    public TnvedDto(Integer id, String code, String name)
    {
        this.id = id;
        this.code = code;
        this.name = name;
    }
}
