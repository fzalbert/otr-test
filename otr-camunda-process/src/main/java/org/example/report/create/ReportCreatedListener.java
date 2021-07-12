package org.example.report.create;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.task.Task;
import org.example.dto.appeal.Appeal;
import org.example.dto.appeal.StatusAppealParser;
import org.example.dto.report.Report;
import org.example.kafka.Message;
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
    private ProcessEngine camunda;

    @Autowired
    private ObjectMapper objectMapper;

    @StreamListener(target = Sink.INPUT,
            condition="(headers['type']?:'')=='ReportCreatedCommand'")
    @Transactional
    public void appealCreatedCommandReceived(String messageJson) throws JsonParseException, JsonMappingException, IOException {

        Message<Report> message = objectMapper.readValue(messageJson, new TypeReference<Message<Report>>(){});
        Report report = message.getData();

        System.out.println("report created: " + report.getId());

        List<Task> tasks = camunda.getTaskService()
                .createTaskQuery()
                .processInstanceBusinessKey(report.getAppeal().getId().toString())
                .list();

        if(tasks.size() < 1)
            return;

        Task task = tasks.get(0);
        

        camunda.getTaskService()
                .complete(task.getId());

    }

}
