package com.example.learning_platform_backend.dto;

import lombok.Data;

@Data
public class UnitCreateDTO {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    private Long subjectId;
}