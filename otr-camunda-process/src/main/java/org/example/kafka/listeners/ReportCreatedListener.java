package org.example.kafka.listeners;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.example.dto.report.Report;
import org.example.service.report.CamundaReportService;
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
public class ReportCreatedListener {

    @Autowired
    private CamundaReportService service;

    @Autowired
    private JsonReaderHelper jsonReader;

    @StreamListener(target = Sink.INPUT,
            condition="(headers['type']?:'')=='ReportCreated'")
    @Transactional
    public void appealCreatedCommandReceived(String messageJson) throws JsonParseException, JsonMappingException, IOException, Exception {

        System.out.println("method appealCreatedCommandReceived: messageJson: " + messageJson);
        Report response = jsonReader.read(messageJson, Report.class);

        if(response != null)
            service.create(response);
    }

}
