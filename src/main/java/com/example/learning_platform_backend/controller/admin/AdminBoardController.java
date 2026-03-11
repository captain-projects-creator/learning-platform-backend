package com.example.learning_platform_backend.controller.admin;

import com.example.learning_platform_backend.entity.Board;
import com.example.learning_platform_backend.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/boards")
@RequiredArgsConstructor
public class AdminBoardController {

    private final BoardRepository boardRepository;

    @GetMapping
    public List<Board> getAll() {
        return boardRepository.findAll();
    }

    @GetMapping("/by-standard/{standardId}")
    public List<Board> getByStandard(@PathVariable Long standardId) {
        return boardRepository.findByStandardId(standardId);
    }

    @PostMapping
    public Board create(@RequestBody Board board) {
        return boardRepository.save(board);
    }

    @PutMapping("/{id}")
    public Board update(@PathVariable Long id, @RequestBody Board board) {
        board.setId(id);
        return boardRepository.save(board);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        boardRepository.deleteById(id);
    }
}