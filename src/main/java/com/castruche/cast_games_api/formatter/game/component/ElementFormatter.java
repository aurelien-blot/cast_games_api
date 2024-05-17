package com.castruche.cast_games_api.formatter.game.component;

import com.castruche.cast_games_api.dto.game.component.ElementDto;
import com.castruche.cast_games_api.entity.game.component.Element;
import com.castruche.cast_games_api.formatter.IFormatter;
import org.springframework.stereotype.Service;

@Service
public class ElementFormatter implements IFormatter<Element, ElementDto> {

    @Override
    public ElementDto entityToDto(Element entity) {
        if(entity == null){
            return null;
        }
        ElementDto elementDto = new ElementDto();
        elementDto.setId(entity.getId());
        return elementDto;
    }

    @Override
    public Element dtoToEntity(ElementDto dto) {
        Element element = new Element();
        element.setId(dto.getId());
        return element;
    }
}
