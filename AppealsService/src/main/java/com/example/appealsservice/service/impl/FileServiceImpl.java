package com.example.appealsservice.service.impl;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.example.appealsservice.domain.File;
import com.example.appealsservice.dto.response.FileDto;
import com.example.appealsservice.exception.NotRightsException;
import com.example.appealsservice.exception.ResourceNotFoundException;
import com.example.appealsservice.repository.AppealRepository;
import com.example.appealsservice.repository.FileRepository;
import com.example.appealsservice.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;
    private final AppealRepository appealRepository;

    public FileServiceImpl(FileRepository fileRepository, AppealRepository appealRepository)
    {
        this.fileRepository = fileRepository;
        this.appealRepository = appealRepository;
    }


    @Override
    public FileDto getById(Long fileId) {

        var file = fileRepository.findById(fileId).orElseThrow(()
                -> new ResourceNotFoundException(fileId));

        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/files/")
                .path(file.getId().toString())
                .toUriString();

        return new FileDto(file, fileDownloadUri, file.getData().length);
    }

    public void store(MultipartFile file, long appealId, long clientId) throws IOException {

        var appeal = appealRepository.findById(appealId).orElseThrow(()
                -> new ResourceNotFoundException(appealId));
        if(appeal.getClientId() != clientId)
            throw new NotRightsException("You can add files only to your appeals");

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        File fileDb = new File();
        fileDb.setName(fileName);
        fileDb.setAppealId(appealId);
        fileDb.setType(file.getContentType());
        fileDb.setData(file.getBytes());

        fileRepository.save(fileDb);

    }

    @Override
    public List<FileDto> getFilesByIdAppealId(Long appealId) {


        var appeal = appealRepository.findById(appealId).orElseThrow(()
                -> new ResourceNotFoundException(appealId));

        var files = fileRepository
                .findAll()
                .stream()
                .filter(x-> x.getAppealId() == appealId)
                .map(x -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/files/")
                    .path(x.getId().toString())
                    .toUriString();

            return new FileDto(x,
                    fileDownloadUri,
                    x.getData().length);
        }).collect(Collectors.toList());

        return files;
    }

    @Override
    public void deleteFile(Long id) {

        var file = fileRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException(id));

        fileRepository.delete(file);

    }
    public File getFile(Long id) {
        return fileRepository.findById(id).get();
    }


}