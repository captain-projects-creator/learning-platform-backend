package com.example.learning_platform_backend.controller.admin;

import com.example.learning_platform_backend.entity.Board;
import com.example.learning_platform_backend.entity.StudyGroup;
import com.example.learning_platform_backend.repository.BoardRepository;
import com.example.learning_platform_backend.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/groups")
@RequiredArgsConstructor
public class AdminGroupController {

    private final GroupRepository groupRepo;
    private final BoardRepository boardRepo;

    @PostMapping
    public StudyGroup create(@RequestBody StudyGroup group) {

        Board board = boardRepo.findById(group.getBoard().getId())
                .orElseThrow(() -> new RuntimeException("Board not found"));

        group.setBoard(board);

        return groupRepo.save(group);
    }

}