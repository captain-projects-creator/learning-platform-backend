package com.example.learning_platform_backend.controller;

import com.example.learning_platform_backend.entity.Progress;
import com.example.learning_platform_backend.entity.Topic;
import com.example.learning_platform_backend.entity.User;
import com.example.learning_platform_backend.repository.ProgressRepository;
import com.example.learning_platform_backend.repository.TopicRepository;
import com.example.learning_platform_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/progress")
@RequiredArgsConstructor
public class ProgressController {

    private final ProgressRepository progressRepo;
    private final TopicRepository topicRepo;
    private final UserRepository userRepo;

    @PostMapping("/topic/update")
    public ResponseEntity<?> updateProgress(
            @RequestBody Map<String, Long> body,
            Authentication authentication) {

        Long topicId = body.get("topicId");

        User user = userRepo
                .findByEmail(authentication.getName())
                .orElseThrow();

        Topic topic = topicRepo
                .findById(topicId)
                .orElseThrow();

        Progress progress = progressRepo
                .findByUserIdAndTopicId(user.getId(), topicId)
                .orElse(new Progress());

        progress.setUser(user);
        progress.setTopic(topic);
        progress.setCompleted(true);

        progressRepo.save(progress);

        return ResponseEntity.ok("Progress Updated");
    }
}