package com.example.learning_platform_backend.controller.admin;

import com.example.learning_platform_backend.dto.QuizCreateDTO;
import com.example.learning_platform_backend.entity.Quiz;
import com.example.learning_platform_backend.entity.Topic;
import com.example.learning_platform_backend.repository.QuizRepository;
import com.example.learning_platform_backend.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/quizzes")
@RequiredArgsConstructor
public class AdminQuizController {

    private final QuizRepository quizRepository;
    private final TopicRepository topicRepository;

    // ================= CREATE QUIZ =================
    @PostMapping
    public Quiz create(@RequestBody QuizCreateDTO dto) {

        // Check if quiz already exists for topic
        List<Quiz> existing = quizRepository.findByTopicId(dto.getTopicId());

        if (!existing.isEmpty()) {
            // Update existing quiz instead of inserting new
            Quiz quiz = existing.get(0);
            quiz.setTitle(dto.getTitle());
            quiz.setTimer(dto.getTimer());
            return quizRepository.save(quiz);
        }

        Topic topic = topicRepository.findById(dto.getTopicId())
                .orElseThrow(() -> new RuntimeException("Topic not found"));

        Quiz quiz = new Quiz();
        quiz.setTopic(topic);
        quiz.setTitle(dto.getTitle());
        quiz.setTimer(dto.getTimer());

        return quizRepository.save(quiz);
    }

    // ================= GET QUIZ BY TOPIC =================
    @GetMapping("/by-topic/{topicId}")
    public List<Quiz> getByTopic(@PathVariable Long topicId) {
        return quizRepository.findByTopicId(topicId);
    }

    // ================= DELETE QUIZ =================
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        quizRepository.deleteById(id);
    }
}