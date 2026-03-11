package com.example.learning_platform_backend.controller;

import com.example.learning_platform_backend.dto.TopicDTO;
import com.example.learning_platform_backend.dto.TopicSimpleDTO;
import com.example.learning_platform_backend.entity.Topic;
import com.example.learning_platform_backend.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/topics")
@RequiredArgsConstructor
public class TopicController {

    private final TopicRepository topicRepo;


    @GetMapping("/{topicId}/next")
    public ResponseEntity<?> getNextTopic(@PathVariable Long topicId) {

        Topic current = topicRepo.findById(topicId).orElseThrow();

        List<Topic> topics = topicRepo
                .findByUnit_IdOrderByIdAsc(current.getUnit().getId());

        for (int i = 0; i < topics.size(); i++) {

            if (topics.get(i).getId().equals(topicId)) {

                if (i + 1 < topics.size()) {
                    Topic next = topics.get(i + 1);

                    TopicSimpleDTO dto = new TopicSimpleDTO();
                    dto.setId(next.getId());
                    dto.setTitle(next.getTitle());

                    return ResponseEntity.ok(dto);
                }
            }
        }

        return ResponseEntity.ok(null);
    }


    // GET topics by unit
    @GetMapping("/unit/{unitId}")
    public List<TopicSimpleDTO> getTopicsByUnit(@PathVariable Long unitId) {

        List<Topic> topics = topicRepo.findByUnit_IdOrderByIdAsc(unitId);

        return topics.stream().map(t -> {
            TopicSimpleDTO dto = new TopicSimpleDTO();
            dto.setId(t.getId());
            dto.setTitle(t.getTitle());
            return dto;
        }).toList();
    }

    // GET single topic (WITH subjectId included)
    @GetMapping("/{id}")
    public TopicDTO getTopic(@PathVariable Long id) {

        Topic topic = topicRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Topic not found"));

        TopicDTO dto = new TopicDTO();
        dto.setId(topic.getId());
        dto.setTitle(topic.getTitle());
        dto.setContent(topic.getContent());
        dto.setUnitId(topic.getUnit().getId());
        dto.setSubjectId(topic.getUnit().getSubject().getId());

        // 🔥 ADD THESE
        dto.setSubjectName(topic.getUnit().getSubject().getName());
        dto.setUnitName(topic.getUnit().getName());

        return dto;
    }
}