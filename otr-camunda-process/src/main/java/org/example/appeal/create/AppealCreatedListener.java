package org.example.appeal.create;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.camunda.bpm.engine.ProcessEngine;
import org.example.dto.appeal.Appeal;
import org.example.dto.appeal.StatusAppealParser;
import org.example.kafka.Message;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;


@Component
@EnableBinding(Sink.class)
public class AppealCreatedListener {

    @Autowired
    private ProcessEngine camunda;


    @Autowired
    private ObjectMapper objectMapper;

    @StreamListener(target = Sink.INPUT,
            condition="(headers['type']?:'')=='AppealCreatedCommand'")
    @Transactional
    public void appealCreatedCommandReceived(String messageJson) throws JsonParseException, JsonMappingException, IOException {

        Message<Appeal> message = objectMapper.readValue(messageJson, new TypeReference<Message<Appeal>>(){});
        Appeal appeal = message.getData();

        System.out.println("appeal: " + appeal.getId());

        camunda.getRuntimeService().createMessageCorrelation(message.getType()) //
                .processInstanceBusinessKey(appeal.getId().toString())
                .setVariable("appeals_id", appeal.getId())
                .setVariable("appeal_client_name", appeal.getClientId())
                .setVariable("appeal_status", StatusAppealParser.toString(appeal.getStatusAppeal()))
                .setVariable("created_at", appeal.getCreateDate())
                .setVariable("appeal_theme", appeal.getTheme().getName())
                .setVariable("appeal_obj", appeal)
                .correlateWithResult();

    }

}
