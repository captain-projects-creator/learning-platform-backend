package com.example.learning_platform_backend.dto;

import lombok.Data;

@Data
public class TopicSimpleDTO {
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private Long id;
    private String title;
}