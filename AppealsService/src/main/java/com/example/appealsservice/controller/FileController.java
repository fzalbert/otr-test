package com.example.appealsservice.controller;


import com.example.appealsservice.domain.File;
import com.example.appealsservice.dto.response.FileDto;
import com.example.appealsservice.exception.ResponseMessage;
import com.example.appealsservice.service.impl.FileServiceImpl;
import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("files")
public class FileController {

    private final FileServiceImpl fileServiceImpl;

    @Autowired
    public FileController(FileServiceImpl fileServiceImpl) {
        this.fileServiceImpl = fileServiceImpl;
    }


    @GetMapping("/files{appealId}")
    public List<FileDto> getFilesByIdAppealId(Long appealId) {
        return fileServiceImpl.getFilesByAppealId(appealId);
    }

    @DeleteMapping("/{id}")
    public void delete(Long id){
        fileServiceImpl.deleteFile(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getFileByte(@PathVariable Long id) {
        File fileDB = fileServiceImpl.getFile(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"")
                .body(fileDB.getData());
    }


    @PostMapping(value="/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestPart("appealId") String appealId,
                                                      @RequestPart("appealId") String clientId,
                                                      @RequestPart("file") List<MultipartFile> files) {
        String message = "";
        try {
            for (MultipartFile fileRequest:
                 files) {
                fileServiceImpl.store(fileRequest, Long.parseLong(appealId), Long.parseLong(clientId));
            }

            message = "Uploaded the file successfully";
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file";

            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @PostMapping("/csv")
    public List<List<String>>  csv(){

        var filename = "C:\\Users\\chaka\\Desktop\\tnved.csv";
        List<List<String>> records = new ArrayList<List<String>>();
        String[] values = null;
        try (CSVReader csvReader = new CSVReader(new FileReader(filename));) {

            while ((values = csvReader.readNext()) != null) {
                records.add(Arrays.asList(values));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return records;
    }
}
