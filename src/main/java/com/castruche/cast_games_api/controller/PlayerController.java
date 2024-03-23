package com.castruche.cast_games_api.controller;

import com.castruche.cast_games_api.dto.UserDto;
import com.castruche.cast_games_api.service.PlayerService;
import com.castruche.cast_games_api.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.castruche.cast_games_api.controller.ConstantUrl.PLAYER;
import static com.castruche.cast_games_api.controller.ConstantUrl.USER;

@RestController
@RequestMapping(PLAYER)
public class PlayerController {

    private final PlayerService playerService;
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

}
