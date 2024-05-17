package com.castruche.cast_games_api.service.game;

import com.castruche.cast_games_api.dao.game.GameRepository;
import com.castruche.cast_games_api.dto.game.GameDto;
import com.castruche.cast_games_api.dto.game.GameExtraLightDto;
import com.castruche.cast_games_api.entity.game.Game;
import com.castruche.cast_games_api.formatter.game.GameFormatter;
import com.castruche.cast_games_api.service.GenericService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService extends GenericService<Game, GameDto> {

    private static final Logger logger = LogManager.getLogger(GameService.class);
    private GameRepository gameRepository;
    private GameFormatter gameFormatter;

    public GameService(GameRepository gameRepository, GameFormatter gameFormatter) {
        super(gameRepository, gameFormatter);
        this.gameRepository = gameRepository;
        this.gameFormatter = gameFormatter;
    }

    public List<GameExtraLightDto> getAllExtraLightDto(){
        List<Game> games = this.getAll();
        return gameFormatter.entityToExtraLightDto(games);
    }

}
