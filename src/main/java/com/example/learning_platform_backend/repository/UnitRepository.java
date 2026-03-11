package com.example.learning_platform_backend.repository;

import com.example.learning_platform_backend.entity.Unit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UnitRepository extends JpaRepository<Unit, Long> {
    List<Unit> findBySubjectIdOrderByOrderIndexAsc(Long subjectId);
    List<Unit> findBySubjectId(Long subjectId);
    Page<Unit> findBySubjectIdOrderByOrderIndexAsc(Long id, Pageable pageable);
}