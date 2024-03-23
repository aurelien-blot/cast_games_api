package com.castruche.cast_games_api.formatter;

import com.castruche.cast_games_api.dto.PlayerDto;
import com.castruche.cast_games_api.dto.PlayerDto;
import com.castruche.cast_games_api.entity.Player;
import com.castruche.cast_games_api.entity.Player;
import org.springframework.stereotype.Service;

@Service
public class PlayerFormatter implements IFormatter<Player, PlayerDto>{

    @Override
    public PlayerDto entityToDto(Player entity) {
        if(entity == null){
            return null;
        }
        PlayerDto playerDto = new PlayerDto();
        playerDto.setId(entity.getId());
        if(entity.getUser() != null){
            playerDto.setUsername(entity.getUser().getUsername());
        }

        return playerDto;
    }

    @Override
    public Player dtoToEntity(PlayerDto dto) {
        Player player = new Player();
        return player;
    }
}
