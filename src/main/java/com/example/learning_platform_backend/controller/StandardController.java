package com.example.learning_platform_backend.controller;
import com.example.learning_platform_backend.entity.Standard;
import com.example.learning_platform_backend.repository.StandardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/standards")
@RequiredArgsConstructor
public class StandardController {

    private final StandardRepository standardRepo;

    @GetMapping
    public List<Standard> getAllStandards() {
        return standardRepo.findAll();
    }

}