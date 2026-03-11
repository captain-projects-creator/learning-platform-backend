package com.example.learning_platform_backend.service;

import com.example.learning_platform_backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final UserRepository userRepo;
    private final SubjectRepository subjectRepo;
    private final UnitRepository unitRepo;
    private final TopicRepository topicRepo;

    public Map<String, Long> getSummary() {

        Map<String, Long> data = new HashMap<>();
        data.put("users", userRepo.count());
        data.put("subjects", subjectRepo.count());
        data.put("units", unitRepo.count());
        data.put("topics", topicRepo.count());

        return data;
    }
}