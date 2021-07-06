package com.example.appealsservice.kafka;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
public class ModelEvent {

    private String email;

    private String name;

    private String content;


}
