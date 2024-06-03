package com.castruche.cast_games_api.dto.game.component;

import com.castruche.cast_games_api.dto.AbstractDto;

public class EventActionDto extends AbstractDto {

    private String type;

    private String condition;

    private String area;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
