package org.example.utils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.appeal.Appeal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class JsonReaderHelper {

    private final ObjectMapper objectMapper;

    public JsonReaderHelper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public <T> T read(String message, Class<T> type){

        T result = null;
        try {
            result = objectMapper.readValue(message, type);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }

        return result;
    }
}
