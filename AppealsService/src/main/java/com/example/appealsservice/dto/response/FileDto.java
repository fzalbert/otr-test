package com.example.appealsservice.dto.response;

import com.example.appealsservice.domain.File;
import com.example.appealsservice.domain.Theme;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;



@Getter
@Setter
public class FileDto {
    private Long id;
    private Long appealId;
    private String name;
    private String url;
    private String type;
    private long size;

    public FileDto()
    {

    }

    public FileDto(File file, String url, long size) {
        if(file == null)
            return;
        id = file.getId();
        appealId = file.getAppealId();
        name = file.getName();
        this.url = url;
        type = file.getType();
        this.size = size;
    }

}