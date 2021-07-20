package org.example.kafka.listeners;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.example.dto.appeal.Appeal;
import org.example.service.appeals.CamundaAppealService;
import org.example.utils.JsonReaderHelper;
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
    private JsonReaderHelper jsonReaderHelper;

    @StreamListener(target = Sink.INPUT,
            condition="(headers['type']?:'')=='AppealCreated'")
    @Transactional
    public void appealCreatedCommandReceived(String messageJson) throws JsonParseException, JsonMappingException, IOException {

        Appeal response = jsonReaderHelper.read(messageJson, Appeal.class);
        if(response != null)
            appealService.create(response);
    }

}
