package com.castruche.cast_games_api.formatter;

import com.castruche.cast_games_api.dto.player.*;
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

    public PlayerProfileDto entityToInternalProfileDto(Player entity) {
        PlayerProfileDto dto = new PlayerProfileDto();
        dto.setId(entity.getId());
        if(entity.getUser() != null){
            dto.setUsername(entity.getUser().getUsername());
        }
        return dto;
    }

    public PlayerProfileDto entityToExternalProfileDto(Player entity) {
        PlayerProfileDto dto = new PlayerProfileDto();
        dto.setId(entity.getId());
        if(entity.getUser() != null){
            dto.setUsername(entity.getUser().getUsername());
        }
        return dto;
    }

    public PlayerSocialDto entityToSocialDto(Player entity) {
        PlayerSocialDto dto = new PlayerSocialDto();
        dto.setId(entity.getId());
        if(entity.getUser() != null){
            dto.setUsername(entity.getUser().getUsername());
        }
        return dto;
    }

    public PlayerHistoryDto entityToHistoryDto(Player entity) {
        PlayerHistoryDto dto = new PlayerHistoryDto();
        dto.setId(entity.getId());
        if(entity.getUser() != null){
            dto.setUsername(entity.getUser().getUsername());
        }
        return dto;
    }

    public PlayerSettingsDto entityToSettingsDto(Player entity) {
        PlayerSettingsDto dto = new PlayerSettingsDto();
        dto.setId(entity.getId());
        if(entity.getUser() != null){
            dto.setUsername(entity.getUser().getUsername());
        }
        return dto;
    }

    public PlayerStatisticsDto entityToStatisticsDto(Player entity) {
        PlayerStatisticsDto dto = new PlayerStatisticsDto();
        dto.setId(entity.getId());
        if(entity.getUser() != null){
            dto.setUsername(entity.getUser().getUsername());
        }
        return dto;
    }

}
