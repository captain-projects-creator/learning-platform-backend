package com.example.learning_platform_backend.dto;

import java.util.List;

public class UnitWithTopicsDTO {
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setTopics(List<TopicSimpleDTO> topics) {
        this.topics = topics;
    }

    public List<TopicSimpleDTO> getTopics() {
        return topics;
    }

    private List<TopicSimpleDTO> topics;
}