package com.example.learning_platform_backend.controller;

import com.example.learning_platform_backend.dto.TopicSimpleDTO;
import com.example.learning_platform_backend.dto.UnitWithTopicsDTO;
import com.example.learning_platform_backend.entity.Topic;
import com.example.learning_platform_backend.entity.Unit;
import com.example.learning_platform_backend.repository.TopicRepository;
import com.example.learning_platform_backend.repository.UnitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/units")
@RequiredArgsConstructor
public class UnitController {

    private final UnitRepository unitRepo;
    private final TopicRepository topicRepo;

    @GetMapping("/subject/{subjectId}")
    public List<UnitWithTopicsDTO> getUnitsBySubject(@PathVariable Long subjectId) {

        List<Unit> units = unitRepo.findBySubjectIdOrderByOrderIndexAsc(subjectId);

        return units.stream().map(unit -> {
            UnitWithTopicsDTO dto = new UnitWithTopicsDTO();
            dto.setId(unit.getId());
            dto.setName(unit.getName());  // ✅ FIXED

            List<Topic> topics =
                    topicRepo.findByUnit_IdOrderByIdAsc(unit.getId());

            dto.setTopics(
                    topics.stream().map(t -> {
                        TopicSimpleDTO td = new TopicSimpleDTO();
                        td.setId(t.getId());
                        td.setTitle(t.getTitle());
                        return td;
                    }).toList()
            );

            return dto;
        }).toList();
    }
}