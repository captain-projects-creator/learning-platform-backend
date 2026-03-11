package com.example.learning_platform_backend.dto;

import lombok.Data;
import java.util.List;

@Data
public class SubjectWithUnitsDTO {
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

    public List<UnitSimpleDTO> getUnits() {
        return units;
    }

    public void setUnits(List<UnitSimpleDTO> units) {
        this.units = units;
    }

    private List<UnitSimpleDTO> units;
}