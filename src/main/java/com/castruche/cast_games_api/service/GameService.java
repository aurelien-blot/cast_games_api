package com.castruche.cast_games_api.service;

import com.castruche.cast_games_api.dao.GameRepository;
import com.castruche.cast_games_api.dto.GameDto;
import com.castruche.cast_games_api.dto.GameExtraLightDto;
import com.castruche.cast_games_api.entity.Game;
import com.castruche.cast_games_api.formatter.GameFormatter;
import com.castruche.cast_games_api.service.util.SettingService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService extends GenericService<Game, GameDto>{

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
