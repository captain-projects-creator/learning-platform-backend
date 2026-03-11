package com.example.learning_platform_backend.repository;

import com.example.learning_platform_backend.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Long> {

    List<Subject> findByBoardId(Long boardId);

    List<Subject> findByGroupId(Long groupId);

    List<Subject> findByStandardId(Long standardId);

    @Query("""
    SELECT s.name, AVG(qa.score)
    FROM QuizAttempt qa
    JOIN qa.quiz q
    JOIN q.topic t
    JOIN t.unit u
    JOIN u.subject s
    GROUP BY s.name
    ORDER BY AVG(qa.score) DESC
    """)
    List<Object[]> getTopSubjects();
}