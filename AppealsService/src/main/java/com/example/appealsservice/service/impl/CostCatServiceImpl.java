package com.example.appealsservice.service.impl;

import com.example.appealsservice.domain.CostCat;
import com.example.appealsservice.domain.TNVED;
import com.example.appealsservice.dto.response.CostCatDto;
import com.example.appealsservice.exception.NotRightsException;
import com.example.appealsservice.exception.ResourceNotFoundException;
import com.example.appealsservice.exception.TemplateException;
import com.example.appealsservice.repository.CostCatRepository;
import com.example.appealsservice.repository.TNVEDRepository;
import com.example.appealsservice.service.CostCatService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Scope("prototype")
@Service
public class CostCatServiceImpl implements CostCatService {

    private final CostCatRepository costCatRepository;

    public CostCatServiceImpl(CostCatRepository costCatRepository) {
        this.costCatRepository = costCatRepository;
    }

    @Override
    public List<CostCatDto> getAll() {

        return costCatRepository
                .findAll()
                .stream()
                .map(CostCatDto::new)
                .collect(Collectors.toList());

    }

    @Override
    public CostCatDto byId(Long id) {
        var cat = costCatRepository.findById(id).orElseThrow(()
                -> new TemplateException("Код затрат не найден"));

        return new CostCatDto(cat);
    }

    /**
     * Создать коды затрат в базе
     */
    public void init() throws URISyntaxException {

        if ((long) costCatRepository.findAll().size() >= 1)
            throw new NotRightsException("costCats already created");

        URL res = getClass().getClassLoader().getResource("costCats.json");
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
                    CostCat costCat = objectMapper.readValue(jsonArray.get(i).toString(), CostCat.class);
                    costCatRepository.save(costCat);
                }

            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }

    }
}
