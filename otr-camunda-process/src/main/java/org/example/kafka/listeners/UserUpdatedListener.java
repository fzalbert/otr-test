package org.example.kafka.listeners;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.user.Employee;
import org.example.service.user.CamundaUserService;
import org.example.utils.JsonReaderHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

public class UserUpdatedListener {

    @Autowired
    private CamundaUserService userService;

    @Autowired
    private JsonReaderHelper jsonReaderHelper;

    @StreamListener(target = Sink.INPUT,
            condition="(headers['type']?:'')=='UserUpdated'")
    @Transactional
    public void appealCreatedCommandReceived(String messageJson) {

        Employee user = jsonReaderHelper.read(messageJson, Employee.class);
        if(user != null)
            userService.update(user);
    }

}
