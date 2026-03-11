package com.example.learning_platform_backend.controller;

import com.example.learning_platform_backend.entity.Subject;
import com.example.learning_platform_backend.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/subjects")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectRepository subjectRepo;

    @GetMapping("/board/{boardId}")
    public List<Subject> getSubjectsByBoard(@PathVariable Long boardId) {
        return subjectRepo.findByBoardId(boardId);
    }


    @GetMapping("/group/{groupId}")
    public List<Subject> getSubjectsByGroup(@PathVariable Long groupId) {
        return subjectRepo.findByGroupId(groupId);
    }


    @GetMapping("/standard/{standardId}")
    public List<Subject> getSubjectsByStandard(@PathVariable Long standardId) {
        return subjectRepo.findByStandardId(standardId);
    }
}