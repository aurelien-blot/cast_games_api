package com.castruche.cast_games_api.formatter;

import com.castruche.cast_games_api.dto.contact.ContactDto;
import com.castruche.cast_games_api.dto.player.*;
import com.castruche.cast_games_api.entity.Player;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlayerFormatter implements IFormatter<Player, PlayerDto>{

    private ContactFormatter contactFormatter;

    public PlayerFormatter(ContactFormatter contactFormatter) {
        this.contactFormatter = contactFormatter;
    }

    @Override
    public PlayerDto entityToDto(Player entity) {
        if(entity == null){
            return null;
        }
        PlayerDto dto = new PlayerDto();
        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        dto.setArchived(entity.isArchived());
        return dto;
    }

    @Override
    public Player dtoToEntity(PlayerDto dto) {
        Player player = new Player();
        return player;
    }

    public PlayerExtraLightDto entityToExtraLightDto(Player entity) {
        if(entity == null){
            return null;
        }
        PlayerExtraLightDto dto = new PlayerExtraLightDto();
        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        return dto;
    }

    public List<PlayerExtraLightDto> entityToExtraLightDto(List<Player> entities) {
        if(entities == null){
            return null;
        }
        List<PlayerExtraLightDto> dtos = new ArrayList<>();
        entities.forEach(entity -> {
            dtos.add(this.entityToExtraLightDto(entity));
        });
        return dtos;
    }

    public PlayerProfileDto entityToInternalProfileDto(Player entity) {
        if(entity == null){
            return null;
        }
        PlayerProfileDto dto = new PlayerProfileDto();
        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        dto.setArchived(entity.isArchived());
        return dto;
    }

    public PlayerProfileDto entityToExternalProfileDto(Player entity) {
        if(entity == null){
            return null;
        }
        PlayerProfileDto dto = new PlayerProfileDto();
        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        dto.setArchived(entity.isArchived());
        if(!dto.isArchived()){

        }
        return dto;
    }

    public PlayerSocialDto entityToSocialDto(Player entity) {
        if(entity == null){
            return null;
        }
        PlayerSocialDto dto = new PlayerSocialDto();
        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        dto.setFriendList(new ArrayList<>());
        dto.setBlockedList(new ArrayList<>());
        if(entity.getContacts()!=null){
            entity.getContacts().forEach(contact -> {
                ContactDto contactDto = this.contactFormatter.entityToDto(contact);
                if(contact.isBlocked()) {
                    dto.getBlockedList().add(contactDto);
                }
                else {
                    dto.getFriendList().add(contactDto);
                }
            });
        }
        return dto;
    }

    public PlayerHistoryDto entityToHistoryDto(Player entity) {
        if(entity == null){
            return null;
        }
        PlayerHistoryDto dto = new PlayerHistoryDto();
        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        return dto;
    }

    public PlayerSettingsDto entityToSettingsDto(Player entity) {
        if(entity == null){
            return null;
        }
        PlayerSettingsDto dto = new PlayerSettingsDto();
        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        return dto;
    }

    public PlayerStatisticsDto entityToStatisticsDto(Player entity) {
        if(entity == null){
            return null;
        }
        PlayerStatisticsDto dto = new PlayerStatisticsDto();
        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        return dto;
    }

}
