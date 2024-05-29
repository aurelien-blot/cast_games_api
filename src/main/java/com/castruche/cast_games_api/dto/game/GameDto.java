package com.castruche.cast_games_api.dto.game;

import com.castruche.cast_games_api.dto.AbstractDto;
import com.castruche.cast_games_api.dto.game.component.ActionDto;
import com.castruche.cast_games_api.dto.game.component.IndividualScoreSheetDto;
import com.castruche.cast_games_api.dto.game.component.PlayerDataDto;

import javax.swing.*;
import java.util.List;

public class GameDto extends AbstractDto {
    private String name;
    private String description;
    private String actionType;

    private PlayerDataDto playerData;

    private String startMode;

    private String endMode;

    private Integer scoreLimit;

    private Boolean showScore;

    private Integer timeAfterRound;

    private List<ActionDto> actionList;

    private List<IndividualScoreSheetDto> individualScoreSheet;

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public PlayerDataDto getPlayerData() {
        return playerData;
    }

    public void setPlayerData(PlayerDataDto playerData) {
        this.playerData = playerData;
    }

    public String getStartMode() {
        return startMode;
    }

    public void setStartMode(String startMode) {
        this.startMode = startMode;
    }

    public String getEndMode() {
        return endMode;
    }

    public void setEndMode(String endMode) {
        this.endMode = endMode;
    }

    public Integer getScoreLimit() {
        return scoreLimit;
    }

    public void setScoreLimit(Integer scoreLimit) {
        this.scoreLimit = scoreLimit;
    }

    public Boolean getShowScore() {
        return showScore;
    }

    public void setShowScore(Boolean showScore) {
        this.showScore = showScore;
    }

    public Integer getTimeAfterRound() {
        return timeAfterRound;
    }

    public void setTimeAfterRound(Integer timeAfterRound) {
        this.timeAfterRound = timeAfterRound;
    }

    public List<ActionDto> getActionList() {
        return actionList;
    }

    public void setActionList(List<ActionDto> actionList) {
        this.actionList = actionList;
    }

    public List<IndividualScoreSheetDto> getIndividualScoreSheet() {
        return individualScoreSheet;
    }

    public void setIndividualScoreSheet(List<IndividualScoreSheetDto> individualScoreSheet) {
        this.individualScoreSheet = individualScoreSheet;
    }

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
