package com.castruche.cast_games_api.entity.game.component;

import com.castruche.cast_games_api.entity.AbstractEntity;
import jakarta.persistence.Entity;

@Entity
public class Color extends AbstractEntity {

    private String name;
    private String hexCode;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHexCode() {
        return hexCode;
    }

    public void setHexCode(String hexCode) {
        this.hexCode = hexCode;
    }
}
