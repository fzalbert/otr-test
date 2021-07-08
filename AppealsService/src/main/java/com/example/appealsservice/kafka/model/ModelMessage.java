package com.example.appealsservice.kafka.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
public class ModelMessage {

    private String email;

    private String name;

    private String content;

    private String subject;

}
