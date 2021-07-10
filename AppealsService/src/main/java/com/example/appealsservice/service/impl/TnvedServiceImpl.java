package com.example.appealsservice.service.impl;

import com.example.appealsservice.domain.Tnved;
import com.example.appealsservice.dto.response.TnvedDto;
import com.example.appealsservice.exception.NotRightsException;
import com.example.appealsservice.repository.TnvedRepository;
import com.example.appealsservice.service.TnvedService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class TnvedServiceImpl implements TnvedService {

    private final TnvedRepository tnvedRepository;

    public TnvedServiceImpl(TnvedRepository tnvedRepository)
    {
        this.tnvedRepository = tnvedRepository;
    }
    @Override
    public void init() throws URISyntaxException {

        if(tnvedRepository.findAll().stream().count() >=1)
            throw new NotRightsException("Tnveds already created");

        URL res = getClass().getClassLoader().getResource("tnved.json");
        var file = Paths.get(res.toURI()).toFile();
        String absolutePath = file.getAbsolutePath();

        JSONParser jsonParser = new JSONParser();

        ObjectMapper objectMapper = new ObjectMapper();

        try (FileReader reader = new FileReader(absolutePath)) {
            Object obj = jsonParser.parse(reader);

            JSONArray jsonArray = (JSONArray) obj;
            if (jsonArray != null) {
                int len = jsonArray.size();
                for (int i = 0; i < len; i++) {
                    Tnved tnved = objectMapper.readValue(jsonArray.get(i).toString(), Tnved.class);
                    tnvedRepository.save(tnved);
                }

            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<TnvedDto> getAll() {
        return null;
    }

    @Override
    public TnvedDto byId(Long id) {
        return null;
    }
}
