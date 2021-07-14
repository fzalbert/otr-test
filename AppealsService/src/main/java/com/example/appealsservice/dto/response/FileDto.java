package com.example.appealsservice.dto.response;

import com.example.appealsservice.domain.File;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileDto {
    private Long id;
    private String name;
    private Long appealId;
    private String url;
    private String type;

    public FileDto(File file)
    {
        if(file == null)
            return;
        id = file.getId();
        name = file.getName();
        appealId = file.getAppealId();
        url = file.getUrl();
        type = file.getType();
    }
}


