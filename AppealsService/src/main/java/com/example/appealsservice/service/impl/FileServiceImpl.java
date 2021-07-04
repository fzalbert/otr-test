package com.example.appealsservice.service.impl;
import java.io.IOException;
import java.util.stream.Stream;

import com.example.appealsservice.domain.File;
import com.example.appealsservice.exception.NotRightsException;
import com.example.appealsservice.exception.ResourceNotFoundException;
import com.example.appealsservice.repository.AppealRepository;
import com.example.appealsservice.repository.FileRepository;
import com.example.appealsservice.service.FilesStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileServiceImpl {

    private final FileRepository fileRepository;
    private final AppealRepository appealRepository;

    public FileServiceImpl(FileRepository fileRepository, AppealRepository appealRepository)
    {
        this.fileRepository = fileRepository;
        this.appealRepository = appealRepository;
    }


    public File store(MultipartFile file, long appealId, long clientId) throws IOException {

        var appeal = appealRepository.findById(appealId).orElseThrow(()
                -> new ResourceNotFoundException(appealId));
        if(appeal.getClientId() != clientId)
            throw new NotRightsException("");

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        File fileDb = new File();
        fileDb.setName(fileName);
        fileDb.setAppealId(appealId);
        fileDb.setType(file.getContentType());
        fileDb.setData(file.getBytes());

        return fileRepository.save(fileDb);

    }

    public File getFile(Long id) {
        return fileRepository.findById(id).get();
    }

    public Stream<File> getAllFiles() {
        return fileRepository.findAll().stream();
    }

}