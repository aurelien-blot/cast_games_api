package com.castruche.cast_games_api.controller;

import com.castruche.cast_games_api.dto.ContactDto;
import com.castruche.cast_games_api.dto.player.*;
import com.castruche.cast_games_api.service.PlayerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.castruche.cast_games_api.controller.ConstantUrl.PLAYER;

@RestController
@RequestMapping(PLAYER)
public class PlayerController {

    private final PlayerService playerService;
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/profile/{id}")
    public PlayerProfileDto getProfile(@PathVariable("id") Long id) {
        return playerService.getProfile(id);
    }

    @GetMapping("/social/{id}")
    public PlayerSocialDto getSocial(@PathVariable("id") Long id) {
        return playerService.getSocial(id);
    }

    @GetMapping("/history/{id}")
    public PlayerHistoryDto getHistory(@PathVariable("id") Long id) {
        return playerService.getHistory(id);
    }

    @GetMapping("/stats/{id}")
    public PlayerStatisticsDto getStatistics(@PathVariable("id") Long id) {
        return playerService.getStatistics(id);
    }

    @GetMapping("/settings/{id}")
    public PlayerSettingsDto getSettings(@PathVariable("id") Long id) {
        return playerService.getSettings(id);
    }

    //Supprimer si non utilisé
    @PostMapping("/search/{username}")
    public List<PlayerExtraLightDto> searchPlayer(@PathVariable("username") String username) {
        return playerService.searchPlayer(username);
    }
}
