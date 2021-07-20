package org.example.kafka.listeners;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.example.dto.appeal.AppealStatusChangedDto;
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
public class AppealStatusChangedListener {

    @Autowired
    private CamundaAppealService appealService;

    @Autowired
    private JsonReaderHelper jsonReader;

    @StreamListener(target = Sink.INPUT,
            condition="(headers['type']?:'')=='AppealStatusChanged'")
    @Transactional
    public void appealCreatedCommandReceived(String messageJson) throws JsonParseException, JsonMappingException, IOException {

        System.out.println("method appealCreatedCommandReceived: messageJson: " + messageJson);
        AppealStatusChangedDto response = jsonReader.read(messageJson, AppealStatusChangedDto.class);

        if(response != null){
            System.out.print("method appealCreatedCommandReceived: response is not null");
            appealService.changeStatus(response);
        }
    }

}

