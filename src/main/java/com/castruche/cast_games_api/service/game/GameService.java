package com.castruche.cast_games_api.service.game;

import com.castruche.cast_games_api.dao.game.GameRepository;
import com.castruche.cast_games_api.dto.game.GameDto;
import com.castruche.cast_games_api.dto.game.GameExtraLightDto;
import com.castruche.cast_games_api.dto.game.component.ActionDto;
import com.castruche.cast_games_api.dto.game.component.IndividualScoreSheetDto;
import com.castruche.cast_games_api.dto.game.component.PlayerDataDto;
import com.castruche.cast_games_api.entity.game.Game;
import com.castruche.cast_games_api.formatter.game.GameFormatter;
import com.castruche.cast_games_api.service.GenericService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public GameDto getYams(){

        GameDto gameDto = new GameDto();
        gameDto.setName("YAM'S");
        gameDto.setActionType("ROUND");

        PlayerDataDto playerDataDto = new PlayerDataDto();
        playerDataDto.setMinPlayer(2);
        playerDataDto.setOrderMode("STANDARD");

        gameDto.setPlayerData(playerDataDto);

        gameDto.setStartMode("RANDOM");
        gameDto.setEndMode("ALL_VALUES_FILLED");
        gameDto.setShowScore(false);
        gameDto.setTimeAfterRound(2);
        gameDto.setActionList(new ArrayList<>());

        ActionDto rollDice = new ActionDto();
        rollDice.setType("ROLL_DICE");
        rollDice.setIndex(1);
        rollDice.setDiceTotal(5);
        rollDice.setRequired(true);
        rollDice.setContinueMode(false);
        gameDto.getActionList().add(rollDice);

        ActionDto checkScoreCell = new ActionDto();
        checkScoreCell.setType("CHECK_SCORE_CELL");
        checkScoreCell.setArea("SCORE_SHEET");
        checkScoreCell.setIndex(2);
        checkScoreCell.setRequired(true);
        checkScoreCell.setContinueMode(false);
        gameDto.getActionList().add(checkScoreCell);

        gameDto.setIndividualScoreSheet(new ArrayList<>());

        int i=1;
        gameDto.getIndividualScoreSheet().add(new IndividualScoreSheetDto(i, "TOTAL_1", "Total de 1", true, "CHECKBOX"));
        gameDto.getIndividualScoreSheet().add(new IndividualScoreSheetDto(i++, "TOTAL_2", "Total de 2", true, "CHECKBOX"));
        gameDto.getIndividualScoreSheet().add(new IndividualScoreSheetDto(i++, "TOTAL_3", "Total de 3", true, "CHECKBOX"));
        gameDto.getIndividualScoreSheet().add(new IndividualScoreSheetDto(i++, "TOTAL_4", "Total de 4", true, "CHECKBOX"));
        gameDto.getIndividualScoreSheet().add(new IndividualScoreSheetDto(i++, "TOTAL_5", "Total de 5", true, "CHECKBOX"));
        gameDto.getIndividualScoreSheet().add(new IndividualScoreSheetDto(i++, "TOTAL_6", "Total de 6", true, "CHECKBOX"));
        gameDto.getIndividualScoreSheet().add(new IndividualScoreSheetDto(i++, "GLOBAL_TOTAL_1", "Total 1", false, null));
        gameDto.getIndividualScoreSheet().add(new IndividualScoreSheetDto(i++, "TOTAL_BONUS", "Si >63 >>> +35", false, null));
        gameDto.getIndividualScoreSheet().add(new IndividualScoreSheetDto(i++, "BRELAN", "Brelan (Total des 3 dés)", true, "CHECKBOX"));
        gameDto.getIndividualScoreSheet().add(new IndividualScoreSheetDto(i++, "CARRE", "Carré (Total des 4 dés)", true, "CHECKBOX"));
        gameDto.getIndividualScoreSheet().add(new IndividualScoreSheetDto(i++, "FULL", "Full (25)", true, "CHECKBOX"));
        gameDto.getIndividualScoreSheet().add(new IndividualScoreSheetDto(i++, "SMALL_STRAIGHT", "Petite suite (30)", true, "CHECKBOX"));
        gameDto.getIndividualScoreSheet().add(new IndividualScoreSheetDto(i++, "LARGE_STRAIGHT", "Grande suite (40)", true, "CHECKBOX"));
        gameDto.getIndividualScoreSheet().add(new IndividualScoreSheetDto(i++, "YAMS", "Yams (50)", true, "CHECKBOX"));
        gameDto.getIndividualScoreSheet().add(new IndividualScoreSheetDto(i++, "CHANCE", "Chance (Total des 5 dés)", true, "CHECKBOX"));
        gameDto.getIndividualScoreSheet().add(new IndividualScoreSheetDto(i++, "GLOBAL_TOTAL_2", "Total 2", false, null));
        gameDto.getIndividualScoreSheet().add(new IndividualScoreSheetDto(i++, "GLOBAL_TOTAL_FINAL", "Grand total", false, null));

        return gameDto;

    }

}
