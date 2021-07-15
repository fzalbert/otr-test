package com.example.appealsservice;

import com.example.appealsservice.domain.CostCat;
import com.example.appealsservice.domain.Theme;
import com.example.appealsservice.exception.NotRightsException;
import com.example.appealsservice.repository.ThemeRepository;
import com.example.appealsservice.service.CostCatService;
import com.example.appealsservice.service.TNVEDService;
import com.example.appealsservice.service.ThemeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

@Component
public class InitialDataLoader implements ApplicationRunner {

    private final TNVEDService tnvedService;
    private final ThemeRepository themeRepository;
    private final CostCatService costCatService;

    @Autowired
    public InitialDataLoader(
            TNVEDService tnvedService,
            ThemeRepository themeRepository,
            CostCatService costCatService
    ) {
        this.tnvedService = tnvedService;
        this.themeRepository = themeRepository;
        this.costCatService = costCatService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            tnvedService.init();
            costCatService.init();
            loadTheme();
        }
        catch (NotRightsException | URISyntaxException  ignored){
        }
    }

    private void loadTheme() throws URISyntaxException {
        if ((long) themeRepository.findAll().size() >= 1)
            throw new NotRightsException("themes already created");

        URL res = getClass().getClassLoader().getResource("themes.json");

        if(res == null)
            return;

        var file = Paths.get(res.toURI()).toFile();
        String absolutePath = file.getAbsolutePath();

        JSONParser jsonParser = new JSONParser();

        ObjectMapper objectMapper = new ObjectMapper();

        try (FileReader reader = new FileReader(absolutePath)) {
            Object obj = jsonParser.parse(reader);

            JSONArray jsonArray = (JSONArray) obj;
            if (jsonArray != null) {
                for (Object o : jsonArray) {
                    Theme theme = objectMapper.readValue(o.toString(), Theme.class);
                    themeRepository.save(theme);
                }
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }
}
