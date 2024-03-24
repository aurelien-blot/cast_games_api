package com.castruche.cast_games_api.dto.player;

import com.castruche.cast_games_api.dto.AbstractDto;

public class PlayerDto extends AbstractDto {
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
