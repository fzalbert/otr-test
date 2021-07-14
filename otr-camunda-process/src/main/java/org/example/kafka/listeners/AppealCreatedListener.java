package org.example.kafka.listeners;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.appeal.Appeal;
import org.example.service.appeals.CamundaAppealService;
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
    private CamundaAppealService appealService;

    @Autowired
    private ObjectMapper objectMapper;

    @StreamListener(target = Sink.INPUT,
            condition="(headers['type']?:'')=='AppealCreated'")
    @Transactional
    public void appealCreatedCommandReceived(String messageJson) throws JsonParseException, JsonMappingException, IOException {

        Appeal appeal = objectMapper.readValue(messageJson, new TypeReference<Appeal>(){});

        appealService.create(appeal);
    }

}
