package com.castruche.cast_games_api.service;

import com.castruche.cast_games_api.dao.PlayerRepository;
import com.castruche.cast_games_api.dto.player.*;
import com.castruche.cast_games_api.dto.util.ConnectedUserDto;
import com.castruche.cast_games_api.entity.Player;
import com.castruche.cast_games_api.entity.User;
import com.castruche.cast_games_api.formatter.PlayerFormatter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class PlayerService extends GenericService<Player, PlayerDto>{

    private static final Logger logger = LogManager.getLogger(PlayerService.class);

    private PlayerRepository playerRepository;
    private PlayerFormatter playerFormatter;
    private ConnectedUserService connectedUserService;

    public PlayerService(PlayerRepository playerRepository, PlayerFormatter playerFormatter,
                         ConnectedUserService connectedUserService){
        super(playerRepository, playerFormatter);
        this.playerRepository = playerRepository;
        this.playerFormatter = playerFormatter;
        this.connectedUserService = connectedUserService;
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
        return this.playerFormatter.entityToSocialDto(player);
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

}
