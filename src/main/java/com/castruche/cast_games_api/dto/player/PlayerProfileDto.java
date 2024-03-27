package com.castruche.cast_games_api.dto.player;

import com.castruche.cast_games_api.dto.AbstractDto;

public class PlayerProfileDto {

    private Long id;
    private String username;

    private boolean archived;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }
}
