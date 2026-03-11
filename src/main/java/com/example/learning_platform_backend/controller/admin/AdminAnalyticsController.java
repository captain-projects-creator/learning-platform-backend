package com.example.learning_platform_backend.controller.admin;

import com.example.learning_platform_backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/analytics")
@RequiredArgsConstructor
public class AdminAnalyticsController {

    private final UserRepository userRepo;
    private final StandardRepository standardRepo;
    private final SubjectRepository subjectRepo;
    private final UnitRepository unitRepo;
    private final TopicRepository topicRepo;
    private final QuizRepository quizRepo;
    private final QuizAttemptRepository attemptRepo;

    @GetMapping("/summary")
    public Map<String, Object> getSummary() {

        Map<String, Object> data = new HashMap<>();

        data.put("totalUsers", userRepo.count());
        data.put("totalStandards", standardRepo.count());
        data.put("totalSubjects", subjectRepo.count());
        data.put("totalUnits", unitRepo.count());
        data.put("totalTopics", topicRepo.count());
        data.put("totalQuizzes", quizRepo.count());
        data.put("totalAttempts", attemptRepo.count());

        return data;
    }

    @GetMapping("/pass-fail-ratio")
    public Map<String,Object> passFailRatio(){

        List<Object[]> result = attemptRepo.getPassFailCounts();

        Object[] row = result.get(0);

        long pass = ((Number) row[0]).longValue();
        long fail = ((Number) row[1]).longValue();

        long total = pass + fail;

        double passPercent = total == 0 ? 0 : pass * 100.0 / total;
        double failPercent = total == 0 ? 0 : fail * 100.0 / total;

        return Map.of(
                "pass", passPercent,
                "fail", failPercent
        );
    }

    @GetMapping("/top-subjects")
    public List<Object[]> topSubjects(){
        return subjectRepo.getTopSubjects();
    }

    @GetMapping("/leaderboard")
    public List<Object[]> leaderboard(){
        return attemptRepo.getLeaderboard();
    }

    @GetMapping("/daily-registrations")
    public List<Object[]> registrations(){
        return userRepo.getDailyRegistrations();
    }

    @GetMapping("/active-users")
    public Long activeUsers() {
        return attemptRepo.getActiveUsersToday();
    }
}