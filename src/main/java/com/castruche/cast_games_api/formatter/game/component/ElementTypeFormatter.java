package com.castruche.cast_games_api.formatter.game.component;

import com.castruche.cast_games_api.dto.game.component.ElementTypeDto;
import com.castruche.cast_games_api.entity.game.component.ElementType;
import com.castruche.cast_games_api.formatter.IFormatter;
import org.springframework.stereotype.Service;

@Service
public class ElementTypeFormatter implements IFormatter<ElementType, ElementTypeDto> {

    @Override
    public ElementTypeDto entityToDto(ElementType entity) {
        if(entity == null){
            return null;
        }
        ElementTypeDto elementTypeDto = new ElementTypeDto();
        elementTypeDto.setId(entity.getId());
        return elementTypeDto;
    }

    @Override
    public ElementType dtoToEntity(ElementTypeDto dto) {
        ElementType elementType = new ElementType();
        elementType.setId(dto.getId());
        return elementType;
    }
}
