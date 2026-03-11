package com.example.learning_platform_backend.controller.admin;

import com.example.learning_platform_backend.dto.SubjectCreateDTO;
import com.example.learning_platform_backend.entity.Board;
import com.example.learning_platform_backend.entity.Standard;
import com.example.learning_platform_backend.entity.StudyGroup;
import com.example.learning_platform_backend.entity.Subject;
import com.example.learning_platform_backend.repository.BoardRepository;
import com.example.learning_platform_backend.repository.StandardRepository;
import com.example.learning_platform_backend.repository.StudyGroupRepository;
import com.example.learning_platform_backend.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/subjects")
@RequiredArgsConstructor
public class AdminSubjectController {

    private final SubjectRepository repo;
    private final BoardRepository boardRepo;
    private final StudyGroupRepository groupRepo;
    private final StandardRepository repoStandard;

    @GetMapping
    public List<Subject> getAll() {
        return repo.findAll();
    }

    @GetMapping("/by-standard/{standardId}")
    public List<Subject> getByStandard(@PathVariable Long standardId) {
        return repo.findByStandardId(standardId);
    }

    @GetMapping("/with-boards")
    public List<Standard> getAllWithBoards() {
        return repoStandard.findAll();
    }

    @GetMapping("/by-board/{boardId}")
    public List<Subject> getByBoard(@PathVariable Long boardId) {
        return repo.findByBoardId(boardId);
    }

    @PostMapping
    public Subject create(@RequestBody SubjectCreateDTO dto) {

        Board board = boardRepo.findById(dto.getBoardId())
                .orElseThrow(() -> new RuntimeException("Board not found"));

        Subject subject = new Subject();
        subject.setName(dto.getName());
        subject.setBoard(board);
        subject.setImageUrl(dto.getImageUrl());

        if(dto.getGroupId()!=null){
            StudyGroup group = groupRepo.findById(dto.getGroupId())
                    .orElseThrow(() -> new RuntimeException("Group not found"));
            subject.setGroup(group);
        }

        return repo.save(subject);
    }

    @PutMapping("/{id}")
    public Subject update(@PathVariable Long id, @RequestBody Subject s) {
        s.setId(id);
        return repo.save(s);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }
}