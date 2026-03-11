package com.example.learning_platform_backend.dto;

import lombok.Data;
import java.util.List;

@Data
public class StandardWithSubjectsUnitsDTO {
    private Long id;
    private String name;
    private List<SubjectWithUnitsDTO> subjects;

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

    public List<SubjectWithUnitsDTO> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<SubjectWithUnitsDTO> subjects) {
        this.subjects = subjects;
    }
}