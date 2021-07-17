package org.example.kafka.listeners;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.task.Task;
import org.example.dto.appeal.Appeal;
import org.example.dto.appeal.StatusAppealParser;
import org.example.dto.report.Report;
import org.example.service.report.CamundaReportService;
import org.example.utils.JsonReaderHelper;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;


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

        Report response = jsonReader.read(messageJson, Report.class);

        if(response != null)
        service.create(response);
    }

}
