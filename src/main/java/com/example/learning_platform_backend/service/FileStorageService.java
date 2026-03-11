package com.example.learning_platform_backend.service;

import com.example.learning_platform_backend.entity.MediaFile;
import com.example.learning_platform_backend.repository.MediaFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileStorageService {

    private final MediaFileRepository mediaRepo;

    private final String uploadDir = "uploads/";

    public MediaFile store(MultipartFile file) {

        try {
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();

            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            File dest = new File(uploadDir + fileName);
            file.transferTo(dest);

            MediaFile media = MediaFile.builder()
                    .fileName(file.getOriginalFilename())
                    .fileUrl("/uploads/" + fileName)
                    .fileType(file.getContentType())
                    .size(file.getSize())
                    .build();

            return mediaRepo.save(media);

        } catch (IOException e) {
            throw new RuntimeException("File upload failed");
        }
    }
}