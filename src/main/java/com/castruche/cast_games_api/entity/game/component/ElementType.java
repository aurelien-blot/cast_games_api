package com.castruche.cast_games_api.entity.game.component;

import com.castruche.cast_games_api.entity.AbstractEntity;
import jakarta.persistence.Entity;

@Entity
public class ElementType extends AbstractEntity {

    private String name;
    private String shortName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
}
