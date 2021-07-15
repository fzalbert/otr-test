package com.example.clientsservice.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(KafkaPro.class)
public class MessageSender {

    @Autowired
    private KafkaPro kafkaProcessor;

    @Autowired
    private ObjectMapper objectMapper;

    public void sendEmail(ModelMessage m) {
        try {
            String jsonMessage = objectMapper.writeValueAsString(m);
            var message = MessageBuilder.withPayload(jsonMessage)
                    .setHeader("type", m.getSubject())
                    .build();
            kafkaProcessor.output1().send(message);
        } catch (Exception e) {
            throw new RuntimeException("Could not tranform and send message due to: " + e.getMessage(), e);
        }
    }
}