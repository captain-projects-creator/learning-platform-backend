package com.example.learning_platform_backend.repository;

import com.example.learning_platform_backend.entity.Progress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.List;

public interface ProgressRepository extends JpaRepository<Progress, Long> {

    Optional<Progress> findByUserIdAndTopicId(Long userId, Long topicId);

    List<Progress> findByUserId(Long userId);

    @Query("""
    SELECT u.email,
    (COUNT(p.id) * 100.0 / (SELECT COUNT(t.id) FROM Topic t))
    FROM Progress p
    JOIN p.user u
    GROUP BY u.email
    """)
    List<Object[]> getUserCompletionPercentage();

    @Query("""
    SELECT u.name,
    AVG(CASE WHEN p.completed = TRUE THEN 1 ELSE 0 END)
    FROM Progress p
    JOIN p.topic t
    JOIN t.unit u
    GROUP BY u.name
    """)
    List<Object[]> getUnitProgressHeatmap();

    Long countByUserIdAndCompletedTrue(Long userId);
}