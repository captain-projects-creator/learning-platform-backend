package com.example.learning_platform_backend.repository;

import com.example.learning_platform_backend.entity.Standard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StandardRepository extends JpaRepository<Standard, Long> {
}