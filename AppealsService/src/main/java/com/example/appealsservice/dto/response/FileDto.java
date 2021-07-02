package com.example.appealsservice.dto.response;

import com.example.appealsservice.domain.File;
import java.util.Date;


public class FileDto {

    private Long id;

    private String url;

    private Date date;

    private Long appealId;

    public FileDto() {}

    public FileDto(File file) {
        id = file.getId();
        url = file.getUrl();
        date = file.getDate();
        appealId = file.getAppeal().getId();
    }
}
