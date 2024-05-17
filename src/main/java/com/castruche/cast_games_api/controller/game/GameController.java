package com.castruche.cast_games_api.controller.game;

import com.castruche.cast_games_api.dto.game.GameDto;
import com.castruche.cast_games_api.dto.game.GameExtraLightDto;
import com.castruche.cast_games_api.service.game.GameService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.castruche.cast_games_api.controller.ConstantUrl.GAME;

@RestController
@RequestMapping(GAME)
public class GameController {

    private final GameService gameService;
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }
    @GetMapping
    public List<GameExtraLightDto> getAll() {
        return gameService.getAllExtraLightDto();
    }

    @GetMapping("/{id}")
    public GameDto getById(@PathVariable("id") Long id) {
        return gameService.selectDtoById(id);
    }
}
