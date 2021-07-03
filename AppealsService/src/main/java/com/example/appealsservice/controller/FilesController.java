package com.example.appealsservice.controller;

import com.example.appealsservice.service.FilesStorageService;
import com.example.appealsservice.service.impl.AppealServiceImpl;
import com.example.appealsservice.service.impl.FilesStorageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("files")
public class FilesController {

    private final FilesStorageServiceImpl filesStorageServiceImpl;

    @Autowired
    public FilesController(FilesStorageServiceImpl filesStorageServiceImpl) {
        this.filesStorageServiceImpl = filesStorageServiceImpl;
    }

    @PostMapping("/upload")
    public void uploadFile(@RequestParam("file") MultipartFile file) {
        filesStorageServiceImpl.save(file);

    }

}