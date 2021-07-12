package org.example.dto.appeal;

import lombok.Getter;
import lombok.Setter;
import org.example.dto.FileDto;
import org.example.dto.TNVED;
import org.example.dto.Theme;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class Appeal {

    private Long id;

    private Long clientId;

    private Theme theme;

    private String description;

    private Date startDate;

    private Date endDate;

    private TNVED tnved;

    private double amount;

    private Date createDate;

    private StatusAppeal statusAppeal;

    private List<FileDto> files;
}