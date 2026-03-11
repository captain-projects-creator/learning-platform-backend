package com.example.learning_platform_backend.controller.admin;

import com.example.learning_platform_backend.dto.StandardCreateDTO;
import com.example.learning_platform_backend.entity.Board;
import com.example.learning_platform_backend.entity.Standard;
import com.example.learning_platform_backend.repository.BoardRepository;
import com.example.learning_platform_backend.repository.StandardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/standards")
@RequiredArgsConstructor
public class AdminStandardController {

    private final StandardRepository standardRepository;
    private final BoardRepository boardRepository;

    @GetMapping
    public List<Standard> getAll() {
        return standardRepository.findAll();
    }

    @GetMapping("/with-subjects")
    public List<Standard> getAllWithSubjects() {
        return standardRepository.findAll();
    }

    @GetMapping("/with-boards")
    public List<Standard> getAllWithBoards() {
        return standardRepository.findAll();
    }

    // 🔥 Only match numeric IDs
    @GetMapping("/{id:\\d+}")
    public Standard getById(@PathVariable Long id) {
        return standardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Standard not found"));
    }

    @PostMapping
    public Standard create(@RequestBody StandardCreateDTO dto) {

        Standard standard = new Standard();
        standard.setName(dto.getName());

        standard.setImageUrl(dto.getImageUrl());

        // Save standard first
        Standard savedStandard = standardRepository.save(standard);

        // 🔥 Create default boards automatically
        Board board1 = new Board();
        board1.setName("TNSCERT - State Board");
        board1.setStandard(savedStandard);

        Board board2 = new Board();
        board2.setName("NCERT - CBSE Board");
        board2.setStandard(savedStandard);

        boardRepository.save(board1);
        boardRepository.save(board2);

        return savedStandard;
    }

    @PutMapping("/{id}")
    public Standard update(@PathVariable Long id, @RequestBody StandardCreateDTO dto) {

        Standard existing = standardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Standard not found"));

        // update name
        existing.setName(dto.getName());

        // ⭐ update image
        existing.setImageUrl(dto.getImageUrl());

        return standardRepository.save(existing);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Standard standard = standardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Standard not found"));

        standardRepository.delete(standard);
        return ResponseEntity.ok().build();
    }
}