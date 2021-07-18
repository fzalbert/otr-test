package com.example.clientsservice.kafka;

import lombok.Data;

/**
 * Модель сообщения для kafka
 */
@Data
public class ModelMessage {

    private String email;

    private String name;

    private String content;

    private String subject;

}
