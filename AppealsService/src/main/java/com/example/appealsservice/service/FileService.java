package com.example.appealsservice.service;

import com.example.appealsservice.dto.response.FileDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {

    FileDto getById(Long fileId);

    void store(MultipartFile file, long appealId, long clientId) throws IOException;

    List<FileDto> getFilesByIdAppealId(Long appealId);

    void deleteFile(Long Id);

}

