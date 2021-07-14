package com.example.appealsservice.messaging;

import com.example.appealsservice.domain.enums.TaskStatus;
import com.example.appealsservice.dto.response.AppealStatusChangedDto;
import com.example.appealsservice.dto.response.ReportDto;
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
            String jsonMessage = objectMapper.writeValueAsString(shortAppealDto);
            var message =  MessageBuilder.withPayload(jsonMessage)
                    .setHeader("type", "AppealCreated")
                    .build();
            kafkaProcessor.output2().send(message);
        } catch (Exception e) {
            throw new RuntimeException("Could not tranform and send message due to: " + e.getMessage(), e);
        }
    }

    public void sendChangeStatus(AppealStatusChangedDto changedStatus) {
        try {
            String jsonMessage = objectMapper.writeValueAsString(changedStatus);
            var message =  MessageBuilder.withPayload(jsonMessage)
                    .setHeader("type", "AppealStatusChanged")
                    .build();
            kafkaProcessor.output2().send(message);
        } catch (Exception e) {
            throw new RuntimeException("Could not tranform and send message due to: " + e.getMessage(), e);
        }
    }

    public void sendReport(ReportDto report) {
        try {
            String jsonMessage = objectMapper.writeValueAsString(report);
            var message =  MessageBuilder.withPayload(jsonMessage)
                    .setHeader("type", "ReportCreated")
                    .build();
            kafkaProcessor.output2().send(message);
        } catch (Exception e) {
            throw new RuntimeException("Could not tranform and send message due to: " + e.getMessage(), e);
        }
    }

}
