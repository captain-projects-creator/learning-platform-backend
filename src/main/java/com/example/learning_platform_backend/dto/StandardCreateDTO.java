package com.example.learning_platform_backend.dto;

import lombok.Data;
import java.util.List;

@Data
public class StandardCreateDTO {

    private String name;

    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getBoardNames() {
        return boardNames;
    }

    public void setBoardNames(List<String> boardNames) {
        this.boardNames = boardNames;
    }

    // We send board names from frontend
    private List<String> boardNames;
}