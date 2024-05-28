package com.castruche.cast_games_api.dto.game.component;

import com.castruche.cast_games_api.dto.AbstractDto;

public class ActionDto extends AbstractDto {

    private String type;
    private Integer index;
    private Integer diceTotal;

    private Boolean required;

    private Boolean continueMode;

    private String area;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getDiceTotal() {
        return diceTotal;
    }

    public void setDiceTotal(Integer diceTotal) {
        this.diceTotal = diceTotal;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public Boolean getContinueMode() {
        return continueMode;
    }

    public void setContinueMode(Boolean continueMode) {
        this.continueMode = continueMode;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
