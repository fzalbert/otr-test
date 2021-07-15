package com.example.appealsservice.controller;


import com.example.appealsservice.dto.response.FileDto;
import com.example.appealsservice.exception.ResponseMessage;
import com.example.appealsservice.service.FileService;
import com.example.appealsservice.service.TNVEDService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.List;

@Scope("prototype")
@RestController
@RequestMapping("files")
public class FileController extends AuthorizeController {

    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService, HttpServletRequest request) {
        super(request);
        this.fileService = fileService;
    }


    @GetMapping("files")
    public List<FileDto> getFilesByIdAppealId(@RequestParam Long appealId) {
        return fileService.getFilesByAppealId(appealId);
    }

    @DeleteMapping("delete")
    public void delete(@RequestParam Long id) {
        fileService.deleteFile(id);
    }


    @PostMapping(value = "upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestPart("appealId") String appealId,
                                                      @RequestPart("file") List<MultipartFile> files) {
        String message = "";
        try {
            for (MultipartFile fileRequest :
                    files) {
                fileService.store(fileRequest, Long.parseLong(appealId), userModel.getId());
            }

            message = "Uploaded the file successfully";
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file";

            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }


    @GetMapping("download")
    public Resource download(@RequestParam Long id){
        return fileService.download(id);
    }



}
