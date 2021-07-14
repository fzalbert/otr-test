package com.example.appealsservice.service.impl;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.example.appealsservice.domain.File;
import com.example.appealsservice.dto.response.FileDto;
import com.example.appealsservice.exception.ResourceNotFoundException;
import com.example.appealsservice.repository.AppealRepository;
import com.example.appealsservice.repository.FileRepository;
import com.example.appealsservice.service.CostCatService;
import com.example.appealsservice.service.FileService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Scope("prototype")
@Service
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;
    private final AppealRepository appealRepository;
    @Value("${path.file}")
    private String pathFile;

    public FileServiceImpl(FileRepository fileRepository, CostCatService costCatService,
                           AppealRepository appealRepository) {
        this.fileRepository = fileRepository;
        this.appealRepository = appealRepository;
    }


    /**
     * получение файла по id
     */
    @Override
    public FileDto getById(Long fileId) {

        var file = fileRepository.findById(fileId).orElseThrow(()
                -> new ResourceNotFoundException(fileId));
        return new FileDto(file);
    }

    /**
     * создание файла
     */
    public void store(MultipartFile fileRequest, Long appealId, Long clientId) throws IOException {

        var filename = fileRequest.getOriginalFilename();
        int lastIndexOf = filename.lastIndexOf(".");

        String name = DigestUtils.md5Hex(StringUtils.cleanPath(Objects.requireNonNull(fileRequest.getOriginalFilename()))
                + new SimpleDateFormat("dd-MM-yyyy").format(new Date())) + filename.substring(lastIndexOf);

        var path = new java.io.File(pathFile + name).getAbsolutePath();
        FileOutputStream outputStream = new FileOutputStream(path);
        outputStream.write(fileRequest.getBytes());
        outputStream.close();

        File fileDb = new File();
        fileDb.setName(name);
        fileDb.setUrl(path);
        fileDb.setAppealId(appealId);
        fileDb.setType(fileRequest.getContentType());

        fileRepository.save(fileDb);

    }


    /**
     * получение файлов по id обращения
     */
    @Override
    public List<FileDto> getFilesByAppealId(Long appealId) {

        var appeal = appealRepository.findById(appealId).orElseThrow(()
                -> new ResourceNotFoundException(appealId));

        return fileRepository
                .findAll()
                .stream()
                .filter(x -> x.getAppealId() == appealId)
                .map(FileDto::new)
                .collect(Collectors.toList());
    }

    /**
     * удалить файл
     */
    @Override
    public void deleteFile(Long id) {

        var file = fileRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException(id));

        fileRepository.delete(file);
    }


    /**
     * удалить файлы по id обращения
     */
    private void deleteFiles(Long appealId) {

        var files = getFilesByAppealId(appealId);
        for (var file :
                files) {
            deleteFile(file.getId());
        }
    }


}