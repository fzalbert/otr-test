package com.example.appealsservice.domain;
import javax.persistence.*;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.net.URI;

@Entity
@Table(name = "files")
@Data
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String url;

    private Long appealId;

    private String type;

    private long size;
}

