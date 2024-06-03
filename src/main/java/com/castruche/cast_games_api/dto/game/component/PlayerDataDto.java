package com.castruche.cast_games_api.dto.game.component;

import com.castruche.cast_games_api.dto.AbstractDto;

public class PlayerDataDto {

    private Integer minPlayer;
    private Integer maxPlayer;

    private String orderMode;

    public Integer getMinPlayer() {
        return minPlayer;
    }

    public void setMinPlayer(Integer minPlayer) {
        this.minPlayer = minPlayer;
    }

    public Integer getMaxPlayer() {
        return maxPlayer;
    }

    public void setMaxPlayer(Integer maxPlayer) {
        this.maxPlayer = maxPlayer;
    }

    public String getOrderMode() {
        return orderMode;
    }

    public void setOrderMode(String orderMode) {
        this.orderMode = orderMode;
    }
}
