package com.castruche.cast_games_api.dto.game;

import com.castruche.cast_games_api.dto.AbstractDto;

public class GameExtraLightDto extends AbstractDto {
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
