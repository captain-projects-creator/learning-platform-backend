package com.example.learning_platform_backend.controller;

import com.example.learning_platform_backend.entity.Board;
import com.example.learning_platform_backend.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardRepository boardRepo;

    @GetMapping("/standard/{standardId}")
    public List<Board> getBoardsByStandard(@PathVariable Long standardId) {
        return boardRepo.findByStandardId(standardId);
    }
}