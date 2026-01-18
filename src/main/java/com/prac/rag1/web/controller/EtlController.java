package com.prac.rag1.web.controller;

import com.prac.rag1.application.service.IngestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/etl")
@RequiredArgsConstructor
public class EtlController {

    private final IngestService ingestService;

    @PostMapping(path = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity upload(@RequestParam("file")MultipartFile file) {
        ingestService.ingest(file);
        return ResponseEntity.ok().build();
    }
}
