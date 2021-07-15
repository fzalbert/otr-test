package com.example.clientsservice.kafka;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface KafkaPro {

    @Output
    MessageChannel output1();
}
