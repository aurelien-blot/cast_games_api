package com.castruche.cast_games_api.dto.util;

import com.castruche.cast_games_api.dto.AbstractDto;

public class ConnectedUserDto extends AbstractDto {
    private Long id;
    private Long playerId;
    private String username;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
