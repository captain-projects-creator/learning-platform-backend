package com.example.learning_platform_backend.repository;

import com.example.learning_platform_backend.entity.User;
import com.example.learning_platform_backend.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    List<User> findByRole(Role role);

    @Query("""
    SELECT DATE(u.createdAt), COUNT(u.id)
    FROM User u
    GROUP BY DATE(u.createdAt)
    ORDER BY DATE(u.createdAt)
    """)
    List<Object[]> getDailyRegistrations();
}