package com.example.appealsservice.service;

import com.example.appealsservice.dto.response.TnvedDto;

import java.net.URISyntaxException;
import java.util.List;

public interface TnvedService {

    public void init() throws URISyntaxException;
    public List<TnvedDto> getAll();
    public TnvedDto byId(Long id);
}
