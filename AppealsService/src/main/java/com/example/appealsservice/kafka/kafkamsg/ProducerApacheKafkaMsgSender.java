package com.example.appealsservice.kafka.kafkamsg;

import java.util.Properties;

import com.example.appealsservice.kafka.ModelEvent;
import com.example.appealsservice.kafka.config.PropertiesConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import lombok.extern.slf4j.Slf4j;

/**
 * Producer using Apache configuration for sending messages
 */
@Component
@Slf4j
public class ProducerApacheKafkaMsgSender {

    KafkaProducer<String,String> msgKafkaProducer;

    @Autowired
    PropertiesConfig config;

    public void sendMsg(String msg) {
        log.info("Msg before Kafka sending " + msg);
        String topic = config.getMsgTopic();
        log.info("Topic : " + topic);

        ProducerRecord<String,String> msgProducer = new ProducerRecord<>(topic,topic,msg);
        msgKafkaProducer.send(msgProducer);
    }

    public void initializeKafkaProducer(){
        if(msgKafkaProducer == null) {
            Properties properties = getProperties();
            msgKafkaProducer = new KafkaProducer<String, String>(properties);
        }
    }

    private Properties getProperties(){
        Properties properties = new Properties();
        properties.put("bootstrap.servers", config.getKafkaServer());
        properties.put("key.serializer", config.getKey());
        properties.put("value.serializer", config.getMsgValue());
        return properties;
    }

    public void sendJson(ModelEvent model) throws JsonProcessingException {
        initializeKafkaProducer();
        log.info("Msg before Kafka sending " + model.toString());
        String topic = config.getMsgTopic();
        log.info("Topic : " + topic);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(model);

        ProducerRecord<String,String> msgProducer = new ProducerRecord<>(topic,topic,json);
        msgKafkaProducer.send(msgProducer);
    }
}
