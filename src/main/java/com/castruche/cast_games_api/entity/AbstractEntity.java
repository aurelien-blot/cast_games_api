package com.castruche.cast_games_api.entity;

import jakarta.persistence.*;

import java.util.Date;

@MappedSuperclass
public abstract class AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    private Date creationTime;

    private Date modificationTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Date getModificationTime() {
        return modificationTime;
    }

    public void setModificationTime(Date modificationTime) {
        this.modificationTime = modificationTime;
    }

    @PrePersist
    protected void onCreate() {
        this.creationTime = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.modificationTime = new Date();
    }
}