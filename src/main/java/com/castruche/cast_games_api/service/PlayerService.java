package com.castruche.cast_games_api.service;

import com.castruche.cast_games_api.dao.ContactRepository;
import com.castruche.cast_games_api.dao.PlayerRepository;
import com.castruche.cast_games_api.dto.ContactDto;
import com.castruche.cast_games_api.dto.player.*;
import com.castruche.cast_games_api.entity.Contact;
import com.castruche.cast_games_api.entity.Player;
import com.castruche.cast_games_api.entity.User;
import com.castruche.cast_games_api.formatter.ContactFormatter;
import com.castruche.cast_games_api.formatter.PlayerFormatter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlayerService extends GenericService<Player, PlayerDto>{

    private static final Logger logger = LogManager.getLogger(PlayerService.class);

    private PlayerRepository playerRepository;
    private PlayerFormatter playerFormatter;
    private ConnectedUserService connectedUserService;
    private ContactRepository contactRepository;

    private ContactFormatter contactFormatter;

    public PlayerService(PlayerRepository playerRepository, PlayerFormatter playerFormatter,
                         ContactRepository contactRepository,
                         ContactFormatter contactFormatter,
                         ConnectedUserService connectedUserService){
        super(playerRepository, playerFormatter);
        this.playerRepository = playerRepository;
        this.playerFormatter = playerFormatter;
        this.contactRepository = contactRepository;
        this.connectedUserService = connectedUserService;
        this.contactFormatter = contactFormatter;
    }

    public Player initPlayer(User user){
        Player player = new Player();
        player.setUser(user);
        player.setArchived(false);
        player.setUsername(user.getUsername());
        player = this.playerRepository.save(player);
        return player;
    }

    public PlayerProfileDto getProfile(Long playerId){
        if(playerId == null){
            return null;
        }
        Player player = this.selectById(playerId);
        boolean isInternalProfile= this.connectedUserService.isCurrentUserByPlayerId(playerId);
        if(isInternalProfile){
            return this.playerFormatter.entityToInternalProfileDto(player);
        }
        return this.playerFormatter.entityToExternalProfileDto(player);
    }

    public PlayerSocialDto getSocial(Long playerId){
        if(playerId == null){
            return null;
        }
        checkPlayerAccess(playerId);
        Player player = this.selectById(playerId);
        PlayerSocialDto result= this.playerFormatter.entityToSocialDto(player);
        result.setRequestList(getRequestListFromPlayer(playerId));
        return result;
    }

    private List<ContactDto> getRequestListFromPlayer(Long playerId){
        List<Contact> contactRequestList = this.contactRepository.findByContactIdAndActiveIsFalseAndBlockedIsFalseAndRejectedIsFalse(playerId);
        return contactFormatter.entityToDto(contactRequestList);
    }

    public PlayerHistoryDto getHistory(Long playerId){
        if(playerId == null){
            return null;
        }
        checkPlayerAccess(playerId);
        Player player = this.selectById(playerId);
        return this.playerFormatter.entityToHistoryDto(player);
    }

    public PlayerSettingsDto getSettings(Long playerId){
        if(playerId == null){
            return null;
        }
        checkPlayerAccess(playerId);
        Player player = this.selectById(playerId);
        return this.playerFormatter.entityToSettingsDto(player);
    }

    public PlayerStatisticsDto getStatistics(Long playerId){
        if(playerId == null){
            return null;
        }
        checkPlayerAccess(playerId);
        Player player = this.selectById(playerId);
        return this.playerFormatter.entityToStatisticsDto(player);
    }

    private void checkPlayerAccess(Long playerId){
        boolean isInternalProfile= this.connectedUserService.isCurrentUserByPlayerId(playerId);
        if(!isInternalProfile){
            throw new AccessDeniedException("You are not allowed to access this profile");
        }
    }

    public void detachPlayerFromUser(Player player){
        if(player != null){
            player.setUser(null);
            player.setArchived(true);
        }
    }

    public List<PlayerExtraLightDto> searchPlayer(String username){
        if(username == null){
            throw new IllegalArgumentException("Username is null");
        }
        List<Player> playerList = this.playerRepository.findByArchivedIsFalseAndUsernameLikeIgnoreCase("%"+username+"%");
        return this.playerFormatter.entityToExtraLightDto(playerList);
    }

}
