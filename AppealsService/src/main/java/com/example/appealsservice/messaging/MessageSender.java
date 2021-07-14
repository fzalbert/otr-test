package com.example.appealsservice.messaging;

import com.example.appealsservice.dto.response.ShortAppealDto;
import com.example.appealsservice.kafka.model.ModelMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(KafkaProcessor.class)
public class MessageSender {

    @Autowired
    private KafkaProcessor kafkaProcessor;

    @Autowired
    private ObjectMapper objectMapper;

    public void sendEmail(ModelMessage m) {
        try {
            // avoid too much magic and transform ourselves
            String jsonMessage = objectMapper.writeValueAsString(m);
            // wrap into a proper message for the transport (Kafka/Rabbit) and send it
            var message =                     MessageBuilder.withPayload(jsonMessage)
                    .setHeader("type", m.getName())
                    .build();
            kafkaProcessor.output1().send(message);
        } catch (Exception e) {
            throw new RuntimeException("Could not tranform and send message due to: " + e.getMessage(), e);
        }
    }

    public void sendAppeal( ShortAppealDto shortAppealDto) {
        try {
            // avoid too much magic and transform ourselves
            String jsonMessage = objectMapper.writeValueAsString(shortAppealDto);
            // wrap into a proper message for the transport (Kafka/Rabbit) and send it
            var message =  MessageBuilder.withPayload(jsonMessage)
                    .setHeader("type", shortAppealDto.getStatusAppeal())
                    .build();
            kafkaProcessor.output2().send(message);
        } catch (Exception e) {
            throw new RuntimeException("Could not tranform and send message due to: " + e.getMessage(), e);
        }
    }
}
