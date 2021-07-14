package com.example.appealsservice.service;

import com.example.appealsservice.dto.response.CostCatDto;

import java.net.URISyntaxException;
import java.util.List;

public interface CostCatService {

    List<CostCatDto> getAll();
    CostCatDto byId(Long id);

    void init() throws URISyntaxException;

}
