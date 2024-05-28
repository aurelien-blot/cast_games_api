package com.castruche.cast_games_api.entity.game.component;

import com.castruche.cast_games_api.entity.AbstractEntity;
import com.castruche.cast_games_api.entity.Player;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Element extends AbstractEntity {

    private String color;

    private String name;

    @ManyToOne(targetEntity = ElementType.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "element_type_id")
    private ElementType elementType;

    private Integer quantity;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public ElementType getElementType() {
        return elementType;
    }

    public void setElementType(ElementType elementType) {
        this.elementType = elementType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
