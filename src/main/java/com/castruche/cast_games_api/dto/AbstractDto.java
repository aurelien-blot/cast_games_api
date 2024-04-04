package com.castruche.cast_games_api.dto;

import jakarta.persistence.*;

import java.time.LocalDate;

public abstract class AbstractDto {

    private Long id;
    private LocalDate creationTime;
    private LocalDate modificationTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDate creationTime) {
        this.creationTime = creationTime;
    }

    public LocalDate getModificationTime() {
        return modificationTime;
    }

    public void setModificationTime(LocalDate modificationTime) {
        this.modificationTime = modificationTime;
    }
}