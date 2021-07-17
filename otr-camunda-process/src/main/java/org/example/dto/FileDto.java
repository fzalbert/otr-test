package org.example.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class FileDto {

    private Long id;
    private String name;
    private Long appealId;
    private String url;
    private String type;
    private long size;
}
