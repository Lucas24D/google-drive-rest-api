package com.ldsa.googledriverestapi.controllers;

import com.ldsa.googledriverestapi.dtos.GoogleDriveFileDTO;
import com.ldsa.googledriverestapi.services.GoogleDriveFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class GoogleDriveFileController {

    private final GoogleDriveFileService googleDriveFileService;

    @GetMapping
    public ResponseEntity<List<GoogleDriveFileDTO>> findAll() {
        return ResponseEntity.ok(googleDriveFileService.findAll());
    }

    @PostMapping(value = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String upload(
            @RequestParam("file") MultipartFile file,
            @RequestParam("path") String path,
            @RequestParam("shared") String shared) {

        path = "".equals(path) ?"Root" : path;
        System.out.println(path);
        return googleDriveFileService.upload(file, path, Boolean.parseBoolean(shared));
    }

    @GetMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        googleDriveFileService.deleteById(id);
    }

    @GetMapping("/download/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void download(@PathVariable String id, HttpServletResponse response) throws IOException {
        googleDriveFileService.download(id, response.getOutputStream());
    }
}
