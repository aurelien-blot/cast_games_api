package com.castruche.cast_games_api.entity.game.component;

import com.castruche.cast_games_api.entity.AbstractEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
public class Element extends AbstractEntity {

    @ManyToOne
    private Color color;

    @ManyToOne
    private ElementType elementType;

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public ElementType getElementType() {
        return elementType;
    }

    public void setElementType(ElementType elementType) {
        this.elementType = elementType;
    }
}
