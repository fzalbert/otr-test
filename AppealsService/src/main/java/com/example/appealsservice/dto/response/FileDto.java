package com.example.appealsservice.dto.response;

import com.example.appealsservice.domain.File;
import com.example.appealsservice.domain.Theme;
import org.springframework.beans.BeanUtils;

import javax.naming.Name;
import java.util.Date;


public class FileDto {

    private Long id;

    private String url;

    private String name;

    private Date date;

    private Long appealId;

    public FileDto() {}

    public FileDto(File file) {
        BeanUtils.copyProperties(file, this);
    }
}
