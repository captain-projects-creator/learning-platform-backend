package com.example.learning_platform_backend.controller.admin;

import com.example.learning_platform_backend.dto.AdminTopicDTO;
import com.example.learning_platform_backend.dto.TopicRequest;
import com.example.learning_platform_backend.entity.Topic;
import com.example.learning_platform_backend.entity.Unit;
import com.example.learning_platform_backend.repository.TopicRepository;
import com.example.learning_platform_backend.repository.UnitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/topics")
@RequiredArgsConstructor
public class AdminTopicController {

    private final TopicRepository repo;
    private final UnitRepository unitRepository;

    @GetMapping("/by-unit/{id}")
    public Page<AdminTopicDTO> getByUnit(
            @PathVariable Long id,
            @PageableDefault(size = 20) Pageable pageable
    ) {

        return repo.findByUnit_IdOrderByIdAsc(id, pageable)
                .map(topic -> {
                    AdminTopicDTO dto = new AdminTopicDTO();
                    dto.setId(topic.getId());
                    dto.setTitle(topic.getTitle());
                    dto.setContent(topic.getContent());
                    dto.setEstimatedMinutes(topic.getEstimatedMinutes());
                    return dto;
                });
    }

    @PostMapping
    public AdminTopicDTO create(@RequestBody TopicRequest request) {

        Unit unit = unitRepository.findById(request.getUnitId())
                .orElseThrow(() -> new RuntimeException("Unit not found"));

        Topic topic = new Topic();
        topic.setTitle(request.getTitle());
        topic.setContent(request.getContent());
        topic.setEstimatedMinutes(request.getEstimatedMinutes());
        topic.setUnit(unit);

        Topic saved = repo.save(topic);

        AdminTopicDTO dto = new AdminTopicDTO();
        dto.setId(saved.getId());
        dto.setTitle(saved.getTitle());
        dto.setContent(saved.getContent());
        dto.setEstimatedMinutes(saved.getEstimatedMinutes());

        return dto;
    }

    @PutMapping("/{id}")
    public AdminTopicDTO update(@PathVariable Long id,
                                @RequestBody TopicRequest request) {

        Topic topic = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Topic not found"));

        topic.setTitle(request.getTitle());
        topic.setContent(request.getContent());
        topic.setEstimatedMinutes(request.getEstimatedMinutes());

        Topic updated = repo.save(topic);

        AdminTopicDTO dto = new AdminTopicDTO();
        dto.setId(updated.getId());
        dto.setTitle(updated.getTitle());
        dto.setContent(updated.getContent());
        dto.setEstimatedMinutes(updated.getEstimatedMinutes());

        return dto;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }
}