package com.example.appealsservice.service;

import com.example.appealsservice.dto.response.FileDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {

    FileDto getById(Long fileId);

    void store(MultipartFile file, Long appealId, Long clientId) throws IOException;

    List<FileDto> getFilesByAppealId(Long appealId);

    void deleteFile(Long Id);

    FileDto getFile(Long id);

}

