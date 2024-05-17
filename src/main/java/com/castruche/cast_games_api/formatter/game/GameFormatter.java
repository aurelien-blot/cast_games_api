package com.castruche.cast_games_api.formatter.game;

import com.castruche.cast_games_api.dto.game.GameDto;
import com.castruche.cast_games_api.dto.game.GameExtraLightDto;
import com.castruche.cast_games_api.entity.game.Game;
import com.castruche.cast_games_api.formatter.IFormatter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameFormatter implements IFormatter<Game, GameDto> {

    @Override
    public GameDto entityToDto(Game entity) {
        if(entity == null){
            return null;
        }
        GameDto gameDto = new GameDto();
        gameDto.setId(entity.getId());
        gameDto.setName(entity.getName());
        gameDto.setDescription(entity.getDescription());
        return gameDto;
    }

    public GameExtraLightDto entityToExtraLightDto(Game entity) {
        if(entity == null){
            return null;
        }
        GameExtraLightDto gameDto = new GameExtraLightDto();
        gameDto.setId(entity.getId());
        gameDto.setName(entity.getName());
        gameDto.setDescription(entity.getDescription());
        return gameDto;
    }

    public List<GameExtraLightDto> entityToExtraLightDto(List<Game> entityList) {
        if(entityList == null){
            return null;
        }
        return entityList.stream().map(this::entityToExtraLightDto).toList();
    }

    @Override
    public Game dtoToEntity(GameDto dto) {
        Game game = new Game();
        game.setId(dto.getId());
        game.setName(dto.getName());
        game.setDescription(dto.getDescription());
        return game;
    }
}
