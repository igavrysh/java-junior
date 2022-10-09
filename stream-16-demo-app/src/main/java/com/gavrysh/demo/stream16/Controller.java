package com.gavrysh.demo.stream16;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.gavrysh.demo.stream16.model.ExcelReadResult;
import com.gavrysh.demo.stream16.model.UploadResult;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class Controller {

    public static final String EXCEL_MEDIA_TYPE = "multipart/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    private final ExcelReader excelReader;
    private final RecordSender recordSender;
    private final AwsS3Saver awsS3Saver;

    @PostMapping(
            value = "/excel",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = EXCEL_MEDIA_TYPE
    )
    public ResponseEntity<Object> upload(
            @RequestParam final MultipartFile file
    ) {
        log.info("Loading excel file {} of size {}", file.getOriginalFilename(), file.getSize());

        try {
            final ExcelReadResult excelReadResult =  excelReader.read(file.getInputStream());
            log.info("Read excel records: {} with failed records: {}",
                    excelReadResult.records(), excelReadResult.failedRows());
            if (!excelReadResult.records().isEmpty()) {
                recordSender.send(excelReadResult.records());
            }
            awsS3Saver.save(file.getOriginalFilename(), file.getBytes());
            return ResponseEntity.accepted()
                    .body(new UploadResult(excelReadResult.records().size(), excelReadResult.failedRows()));
        } catch (final IOException ex) {
            log.error("Failed to read Excel: {}", ex.getMessage());
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
