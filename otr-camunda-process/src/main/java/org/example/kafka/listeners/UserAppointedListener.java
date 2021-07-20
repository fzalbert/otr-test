package org.example.kafka.listeners;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.example.dto.appeal.AppealAppointedResponse;
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
public class UserAppointedListener {

    @Autowired
    private CamundaAppealService appealService;

    @Autowired
    private JsonReaderHelper jsonReaderHelper;

    @StreamListener(target = Sink.INPUT,
            condition="(headers['type']?:'')=='AppealAppointed'")
    @Transactional
    public void appealCreatedCommandReceived(String messageJson) throws JsonParseException, JsonMappingException, IOException {

        AppealAppointedResponse response = jsonReaderHelper.read(messageJson, AppealAppointedResponse.class);
        if(response != null)
            appealService.appoint(response.getLogin(), response.getAppealId());
    }

}