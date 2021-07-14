package com.example.employeesservice.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

    @Component
    @EnableBinding(KafkaPro.class)
    public class KafkaSender {

        @Autowired
        private KafkaPro kafkaProcessor;

        @Autowired
        private ObjectMapper objectMapper;

        public void sendEmployee(EmployeeModel m) {
            try {
                // avoid too much magic and transform ourselves
                String jsonMessage = objectMapper.writeValueAsString(m);
                // wrap into a proper message for the transport (Kafka/Rabbit) and send it
                var message =                     MessageBuilder.withPayload(jsonMessage)
                        .setHeader("type", "UserCreated")
                        .build();
                kafkaProcessor.output2().send(message);
            } catch (Exception e) {
                throw new RuntimeException("Could not tranform and send message due to: " + e.getMessage(), e);
            }
        }
}
