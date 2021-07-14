package org.example.kafka;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;


public interface KafkaProcessor {

    @Input
    SubscribableChannel input();

    @Output
    MessageChannel output1();

    @Output
    MessageChannel output2();
}
