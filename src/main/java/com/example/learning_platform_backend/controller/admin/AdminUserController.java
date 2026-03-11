package com.example.learning_platform_backend.controller.admin;

import com.example.learning_platform_backend.entity.Role;
import com.example.learning_platform_backend.entity.User;
import com.example.learning_platform_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
public class AdminUserController {

    private final UserRepository userRepo;

    @GetMapping
    public List<User> getAll() {
        return userRepo.findAll();
    }

    @PutMapping("/{id}")
    public User update(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        return userRepo.save(user);
    }

    @PutMapping("/{id}/role")
    public User updateUserRole(@PathVariable Long id,
                               @RequestBody Map<String, String> body) {

        User user = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        try {
            Role role = Role.valueOf(body.get("role").toUpperCase());
            user.setRole(role);
        } catch (Exception e) {
            throw new RuntimeException("Invalid role value");
        }

        return userRepo.save(user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userRepo.deleteById(id);
    }
}