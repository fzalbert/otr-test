package com.example.appealsservice.service;

import com.example.appealsservice.dto.response.TNVEDDto;

import java.net.URISyntaxException;
import java.util.List;

public interface TNVEDService {

    public void init() throws URISyntaxException;
    public List<TNVEDDto> getAll();
    public TNVEDDto byId(Long id);
}
