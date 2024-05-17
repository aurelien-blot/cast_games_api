package com.castruche.cast_games_api.formatter.game.component;

import com.castruche.cast_games_api.dto.game.component.ColorDto;
import com.castruche.cast_games_api.entity.game.component.Color;
import com.castruche.cast_games_api.formatter.IFormatter;
import org.springframework.stereotype.Service;

@Service
public class ColorFormatter implements IFormatter<Color, ColorDto> {

    @Override
    public ColorDto entityToDto(Color entity) {
        if(entity == null){
            return null;
        }
        ColorDto colorDto = new ColorDto();
        colorDto.setId(entity.getId());
        return colorDto;
    }

    @Override
    public Color dtoToEntity(ColorDto dto) {
        Color color = new Color();
        color.setId(dto.getId());
        return color;
    }
}
