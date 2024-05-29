package com.castruche.cast_games_api.dto.game.component;

import com.castruche.cast_games_api.dto.AbstractDto;

public class IndividualScoreSheetDto extends AbstractDto {

    private Integer index;
    private String key;
    private String label;
    private Boolean editable;
    private String type;

    public IndividualScoreSheetDto() {
    }

    public IndividualScoreSheetDto(Integer index, String key, String label, Boolean editable, String type) {
        this.index = index;
        this.key = key;
        this.label = label;
        this.editable = editable;
        this.type = type;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Boolean getEditable() {
        return editable;
    }

    public void setEditable(Boolean editable) {
        this.editable = editable;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
