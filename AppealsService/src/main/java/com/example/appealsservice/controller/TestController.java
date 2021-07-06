package com.example.appealsservice.controller;

import com.example.appealsservice.kafka.ModelEvent;
import com.example.appealsservice.kafka.kafkamsg.ProducerApacheKafkaMsgSender;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("kafka")
public class TestController {

    @Autowired
    private ProducerApacheKafkaMsgSender kafkaMsgSender;

    @PostMapping("/msg")
    public void sendMsg(@RequestBody String msg) {
        log.info("Received msg : " + msg);
        kafkaMsgSender.initializeKafkaProducer();
        kafkaMsgSender.sendMsg(msg);
    }

    @PostMapping("/information")
    public void sendJsonMsg(@RequestBody ModelEvent informationModel) throws JsonProcessingException {
        log.info("Received Json : " + informationModel);
        kafkaMsgSender.initializeKafkaProducer();
        kafkaMsgSender.sendJson(informationModel);
    }
}
