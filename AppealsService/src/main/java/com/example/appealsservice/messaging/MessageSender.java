package com.example.appealsservice.messaging;

import com.example.appealsservice.kafka.model.ModelMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(Source.class)
public class MessageSender {

    @Autowired
    private MessageChannel output;

    @Autowired
    private ObjectMapper objectMapper;

    public void send(ModelMessage m) {
        try {
            // avoid too much magic and transform ourselves
            String jsonMessage = objectMapper.writeValueAsString(m);
            // wrap into a proper message for the transport (Kafka/Rabbit) and send it
            var message =                     MessageBuilder.withPayload(jsonMessage)
                    .setHeader("type", m.getName())
                    .build();
            output.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Could not tranform and send message due to: " + e.getMessage(), e);
        }
    }
}
