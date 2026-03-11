package com.example.learning_platform_backend.dto;

import lombok.Data;

@Data
public class QuizCreateDTO {
    private Long topicId;
    private String title;

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getTimer() {
        return timer;
    }

    public void setTimer(Integer timer) {
        this.timer = timer;
    }

    private Integer timer;
}