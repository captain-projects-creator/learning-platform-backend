package com.example.learning_platform_backend.controller.admin;

import com.example.learning_platform_backend.entity.MediaFile;
import com.example.learning_platform_backend.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/admin/media")
@RequiredArgsConstructor
public class AdminMediaController {

    private final FileStorageService fileStorageService;

    @PostMapping("/upload")
    public MediaFile upload(@RequestParam("file") MultipartFile file) {
        return fileStorageService.store(file);
    }
}