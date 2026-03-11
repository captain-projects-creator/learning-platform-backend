package com.example.learning_platform_backend.controller;

import com.example.learning_platform_backend.entity.StudyGroup;
import com.example.learning_platform_backend.repository.GroupRepository;
import com.example.learning_platform_backend.repository.StudyGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class GroupController {

    private final StudyGroupRepository groupRepo;

    @GetMapping("/{standardId}/{boardId}")
    public List<StudyGroup> getGroups(
            @PathVariable Long standardId,
            @PathVariable Long boardId) {

        return groupRepo.findByStandardIdAndBoardId(standardId, boardId);
    }
}