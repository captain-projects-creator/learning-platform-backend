package com.example.learning_platform_backend.controller.admin;

import com.example.learning_platform_backend.dto.QuestionRequest;
import com.example.learning_platform_backend.entity.Question;
import com.example.learning_platform_backend.entity.Quiz;
import com.example.learning_platform_backend.repository.QuestionRepository;
import com.example.learning_platform_backend.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/questions")
@RequiredArgsConstructor
public class AdminQuestionController {

    private final QuestionRepository questionRepository;
    private final QuizRepository quizRepository;

    @PostMapping
    public Question create(@RequestBody QuestionRequest request) {

        Quiz quiz = quizRepository.findById(request.getQuizId())
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        Question question = new Question();
        question.setQuestionText(request.getQuestionText());
        question.setOptionA(request.getOptionA());
        question.setOptionB(request.getOptionB());
        question.setOptionC(request.getOptionC());
        question.setOptionD(request.getOptionD());
        question.setCorrectAnswer(request.getCorrectAnswer());
        question.setQuestionOrder(request.getQuestionOrder());
        question.setMarks(request.getMarks());
        question.setQuiz(quiz);

        return questionRepository.save(question);
    }

    @PutMapping("/{id}")
    public Question update(@PathVariable Long id,
                           @RequestBody QuestionRequest request) {

        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found"));

        question.setQuestionText(request.getQuestionText());
        question.setOptionA(request.getOptionA());
        question.setOptionB(request.getOptionB());
        question.setOptionC(request.getOptionC());
        question.setOptionD(request.getOptionD());
        question.setCorrectAnswer(request.getCorrectAnswer());
        question.setQuestionOrder(request.getQuestionOrder());
        question.setMarks(request.getMarks());

        return questionRepository.save(question);
    }

    @GetMapping("/by-quiz/{quizId}")
    public List<Question> getByQuiz(@PathVariable Long quizId) {
        return questionRepository.findByQuizIdOrderByQuestionOrderAsc(quizId);
    }
}