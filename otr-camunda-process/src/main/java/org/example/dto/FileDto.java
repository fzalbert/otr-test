package org.example.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileDto {

    private Long id;
    private String name;
    private Long appealId;
    private String url;
    private String type;
    private long size;
}
