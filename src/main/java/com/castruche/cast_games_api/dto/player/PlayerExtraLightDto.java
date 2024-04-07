package com.castruche.cast_games_api.dto.player;

import com.castruche.cast_games_api.dto.AbstractDto;

public class PlayerExtraLightDto{
    private String username;

    private Long id;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
