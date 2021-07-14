package org.example.kafka;

import org.example.dto.appeal.Appeal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@EnableBinding(KafkaProcessor.class)
//@EnableBinding(Source.class)
public class MessageSenderService {

    @Autowired
    private KafkaProcessor processor;

    @Autowired
    private ObjectMapper objectMapper;


    public void send(Appeal m) {
        try {

            String jsonMessage = objectMapper.writeValueAsString(m);

            processor.output1().send(
                    MessageBuilder.withPayload(jsonMessage).setHeader("type", m.getDescription()).build());

            processor.output2().send(
                    MessageBuilder.withPayload(jsonMessage).setHeader("type", m.getDescription()).build());

        } catch (Exception e) {
            throw new RuntimeException("Could not tranform and send message due to: "+ e.getMessage(), e);
        }
    }
}