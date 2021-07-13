package com.example.appealsservice.service.impl;

import com.example.appealsservice.domain.TNVED;
import com.example.appealsservice.dto.response.TNVEDDto;
import com.example.appealsservice.exception.NotRightsException;
import com.example.appealsservice.repository.TNVEDRepository;
import com.example.appealsservice.service.TNVEDService;
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
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TNVEDServiceImpl implements TNVEDService {

    private final TNVEDRepository tnvedRepository;

    public TNVEDServiceImpl(TNVEDRepository tnvedRepository)
    {
        this.tnvedRepository = tnvedRepository;
    }

    /** создать коды ТН ВЭД   */
    @Override
    public void init() throws URISyntaxException {

        if((long) tnvedRepository.findAll().size() >=1)
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
                    TNVED tnved = objectMapper.readValue(jsonArray.get(i).toString(), TNVED.class);
                    tnvedRepository.save(tnved);
                }

            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }

    }

    /** получить лист коды ТН ВЭД   */
    @Override
    public List<TNVEDDto> getAll() {

       return tnvedRepository
               .findAll()
               .stream()
               .map(TNVEDDto::new)
               .collect(Collectors.toList());
    }

    /** получить по id   */
    @Override
    public TNVEDDto byId(Long id) {
        return null;
    }
}
