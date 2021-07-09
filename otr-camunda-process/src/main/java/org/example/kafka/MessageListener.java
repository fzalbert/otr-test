package org.example.kafka;

import java.io.IOException;

import org.camunda.bpm.engine.ProcessEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@EnableBinding(Sink.class)
public class MessageListener {

//    @Autowired
//    private ProcessEngine camunda;

    @Autowired
    private ObjectMapper objectMapper;

    @StreamListener(target = Sink.INPUT,
            condition="(headers['type']?:'')=='RetrievePaymentCommand'")
    @Transactional
    public void retrievePaymentCommandReceived(String messageJson) throws JsonParseException, JsonMappingException, IOException {
        System.out.println(messageJson);
    }

}