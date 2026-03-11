package com.example.learning_platform_backend.repository;

import com.example.learning_platform_backend.entity.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TopicRepository extends JpaRepository<Topic, Long> {

    List<Topic> findByUnit_IdOrderByIdAsc(Long unitId);
    Page<Topic> findByUnit_IdOrderByIdAsc(Long unitId, Pageable pageable);

}