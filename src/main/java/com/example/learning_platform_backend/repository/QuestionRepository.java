package com.example.learning_platform_backend.repository;

import com.example.learning_platform_backend.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findByQuizId(Long quizId);
    List<Question> findByQuizIdOrderByQuestionOrderAsc(Long quizId);

}