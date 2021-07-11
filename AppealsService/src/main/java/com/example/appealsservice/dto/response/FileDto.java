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
    private String name;
    private Long appealId;
    private String url;
    private String type;
    private long size;

    public FileDto(Long id, Long appealId, String name, String url, String type, long size) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.appealId = appealId;
        this.type = type;
        this.size = size;
    }

//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public Long getAppealId() {
//        return appealId;
//    }
//
//    public void setAppealId(Long appealId) {
//        this.appealId = appealId;
//    }
//
//    public String getUrl() {
//        return url;
//    }
//
//    public void setUrl(String url) {
//        this.url = url;
//    }
//
//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
//
//    public long getSize() {
//        return size;
//    }
//
//    public void setSize(long size) {
//        this.size = size;
//    }
}

