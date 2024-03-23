package com.castruche.cast_games_api.service;

import com.castruche.cast_games_api.dao.PlayerRepository;
import com.castruche.cast_games_api.dto.PlayerDto;
import com.castruche.cast_games_api.entity.Player;
import com.castruche.cast_games_api.entity.User;
import com.castruche.cast_games_api.formatter.PlayerFormatter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class PlayerService extends GenericService<Player, PlayerDto>{

    private static final Logger logger = LogManager.getLogger(PlayerService.class);

    private PlayerRepository playerRepository;
    private PlayerFormatter playerFormatter;

    public PlayerService(PlayerRepository playerRepository, PlayerFormatter playerFormatter){
        super(playerRepository, playerFormatter);
        this.playerRepository = playerRepository;
        this.playerFormatter = playerFormatter;
    }

    public Player initPlayer(User user){
        Player player = new Player();
        player.setUser(user);
        player = this.playerRepository.save(player);
        return player;
    }

}
