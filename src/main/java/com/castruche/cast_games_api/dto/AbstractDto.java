package com.castruche.cast_games_api.dto;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

public abstract class AbstractDto {

    private Long id;
    private LocalDateTime creationTime;
    private LocalDateTime modificationTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public LocalDateTime getModificationTime() {
        return modificationTime;
    }

    public void setModificationTime(LocalDateTime modificationTime) {
        this.modificationTime = modificationTime;
    }
}