package com.example.learning_platform_backend.controller;

import com.example.learning_platform_backend.dto.QuizCreateDTO;
import com.example.learning_platform_backend.dto.QuizSubmitRequest;
import com.example.learning_platform_backend.entity.*;
import com.example.learning_platform_backend.repository.ProgressRepository;
import com.example.learning_platform_backend.repository.QuestionRepository;
import com.example.learning_platform_backend.repository.QuizRepository;
import com.example.learning_platform_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/quiz")
@RequiredArgsConstructor
public class QuizController {

    private final QuizRepository quizRepo;
    private final UserRepository userRepo;
    private final ProgressRepository progressRepo;
    private final QuestionRepository questionRepo;

    @GetMapping("/{topicId}")
    public ResponseEntity<?> getQuizByTopic(@PathVariable Long topicId) {

        List<Quiz> quizzes = quizRepo.findByTopicId(topicId);

        if (quizzes.isEmpty()) {
            throw new RuntimeException("Quiz not found");
        }

        Quiz quiz = quizzes.get(0);

        List<Question> questions = questionRepo.findByQuizId(quiz.getId());

        // 🔥 Shuffle question order
        Collections.shuffle(questions);

        List<Map<String, Object>> response = questions.stream().map(q -> {

            List<String> options = new ArrayList<>();
            options.add(q.getOptionA());
            options.add(q.getOptionB());
            options.add(q.getOptionC());
            options.add(q.getOptionD());

            // 🔥 Shuffle options
            Collections.shuffle(options);

            Map<String, Object> map = new HashMap<>();
            map.put("id", q.getId());
            map.put("quizId", quiz.getId());
            map.put("questionText", q.getQuestionText());
            map.put("options", options);

            return map;

        }).toList();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/submit")
    public ResponseEntity<?> submitQuiz(
            @RequestBody QuizSubmitRequest request,
            Authentication authentication) {

        Quiz quiz = quizRepo.findById(request.getQuizId()).orElseThrow();
        List<Question> questions = questionRepo.findByQuizId(quiz.getId());

        int score = 0;

        for (Question question : questions) {

            String userAnswer = request.getAnswers().get(question.getId());

            String correctText = switch (question.getCorrectAnswer()) {
                case "A" -> question.getOptionA();
                case "B" -> question.getOptionB();
                case "C" -> question.getOptionC();
                case "D" -> question.getOptionD();
                default -> "";
            };

            if (userAnswer != null && userAnswer.equals(correctText)) {
                score++;
            }
        }

        int total = questions.size();
        double percentage = total == 0 ? 0 : ((double) score / total) * 100;

        // 🔥 PASS MARK
        if (percentage >= 50) {

            User user = userRepo
                    .findByEmail(authentication.getName())
                    .orElseThrow();

            Topic topic = quiz.getTopic();

            Progress progress = progressRepo
                    .findByUserIdAndTopicId(user.getId(), topic.getId())
                    .orElse(new Progress());

            progress.setUser(user);
            progress.setTopic(topic);
            progress.setCompleted(true);

            progressRepo.save(progress);
        }

        return ResponseEntity.ok(
                Map.of(
                        "score", score,
                        "total", total,
                        "percentage", percentage
                )
        );
    }
}