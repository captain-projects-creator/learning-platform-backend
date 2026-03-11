package com.example.learning_platform_backend.repository;

import com.example.learning_platform_backend.entity.MediaFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaFileRepository extends JpaRepository<MediaFile, Long> {
}