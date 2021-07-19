package com.example.clientsservice.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Log4j
@Component
@EnableBinding(KafkaPro.class)
public class MessageSender {

    @Autowired
    private KafkaPro kafkaProcessor;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Сообщение об успешной регистрации клиента
     * @param m
     */
    public void sendEmail(ModelMessage m) {
        log.debug("kafka method: sendEmail. ModelMessage = " + m);
        try {
            String jsonMessage = objectMapper.writeValueAsString(m);
            log.debug("kafka method: jsonMessage = " + jsonMessage);
            var message = MessageBuilder.withPayload(jsonMessage)
                    .setHeader("type", m.getSubject())
                    .build();
            kafkaProcessor.output1().send(message);
            log.debug("kafka method: kafkaProcessor has send message^ " + message);
        } catch (Exception e) {
            log.debug("kafka method: exception:" + e.getMessage());
            throw new RuntimeException("Could not tranform and send message due to: " + e.getMessage(), e);
        }
    }
}