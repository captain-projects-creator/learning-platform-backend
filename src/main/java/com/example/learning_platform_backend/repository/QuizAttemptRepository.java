package com.example.learning_platform_backend.repository;

import com.example.learning_platform_backend.entity.QuizAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface QuizAttemptRepository extends JpaRepository<QuizAttempt, Long> {

    List<QuizAttempt> findByUserId(Long userId);

    @Query("""
SELECT 
COALESCE(SUM(CASE WHEN qa.passed = TRUE THEN 1 ELSE 0 END),0),
COALESCE(SUM(CASE WHEN qa.passed = FALSE THEN 1 ELSE 0 END),0)
FROM QuizAttempt qa
""")
    List<Object[]> getPassFailCounts();

    @Query("""
    SELECT q.title, COUNT(qa.id)
    FROM QuizAttempt qa
    JOIN qa.quiz q
    GROUP BY q.title
    ORDER BY COUNT(qa.id) DESC
    """)
    List<Object[]> getMostAttemptedQuiz();

    @Query("""
    SELECT COUNT(DISTINCT qa.user.id)
    FROM QuizAttempt qa
    WHERE DATE(qa.attemptedAt) = CURRENT_DATE
    """)
    Long getActiveUsersToday();

    @Query("""
    SELECT u.email, SUM(qa.score)
    FROM QuizAttempt qa
    JOIN qa.user u
    GROUP BY u.email
    ORDER BY SUM(qa.score) DESC
    """)
    List<Object[]> getLeaderboard();

    List<QuizAttempt> findByAttemptedAtAfter(LocalDateTime dateTime);

    List<QuizAttempt> findByQuizId(Long quizId);

    Long countByUserId(Long userId);
}