package com.castruche.cast_games_api.entity.util;

import com.castruche.cast_games_api.entity.AbstractEntity;
import jakarta.persistence.Entity;

import java.util.Date;

@Entity
public class Setting extends AbstractEntity {

    private String shortName;

    private String label;

    private String value;

    private Date dateValue;

    private String description;

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getDateValue() {
        return dateValue;
    }

    public void setDateValue(Date dateValue) {
        this.dateValue = dateValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
