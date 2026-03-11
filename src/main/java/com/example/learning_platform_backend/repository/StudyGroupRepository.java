package com.example.learning_platform_backend.repository;

import com.example.learning_platform_backend.entity.StudyGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudyGroupRepository extends JpaRepository<StudyGroup, Long> {

    List<StudyGroup> findByStandardIdAndBoardId(Long standardId, Long boardId);

}