package com.castruche.cast_games_api.entity.game;

import com.castruche.cast_games_api.entity.AbstractEntity;
import jakarta.persistence.Entity;

@Entity
public class Game extends AbstractEntity {
    private String name;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
