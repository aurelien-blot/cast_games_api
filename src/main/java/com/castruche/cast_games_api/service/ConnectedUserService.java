package com.castruche.cast_games_api.service;

import com.castruche.cast_games_api.dao.UserRepository;
import com.castruche.cast_games_api.dto.standardResponse.BooleanResponseDto;
import com.castruche.cast_games_api.dto.user.UserDto;
import com.castruche.cast_games_api.dto.util.ConnectedUserDto;
import com.castruche.cast_games_api.dto.util.PasswordDto;
import com.castruche.cast_games_api.entity.Player;
import com.castruche.cast_games_api.entity.User;
import com.castruche.cast_games_api.formatter.UserFormatter;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ConnectedUserService {

    private static final Logger logger = LogManager.getLogger(ConnectedUserService.class);

    private UserRepository userRepository;
    private UserFormatter userFormatter;

    public ConnectedUserService(UserRepository userRepository, UserFormatter userFormatter) {
        this.userRepository = userRepository;
        this.userFormatter = userFormatter;
    }

    public ConnectedUserDto getCurrentUser() {
        User user = this.getCurrentUserEntity();
        return this.userFormatter.entityToConnectedUserDto(user);
    }

    public Player getCurrentPlayer() {
        User user = this.getCurrentUserEntity();
        return user.getPlayer();
    }

    public User getCurrentUserEntity() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        String username=authentication.getName();
        return this.userRepository.findByUsername(username);
    }

    public boolean isCurrentUserByPlayerId(long playerId){
        ConnectedUserDto connectedUserDto = this.getCurrentUser();
        return connectedUserDto != null && connectedUserDto.getPlayerId() != null && connectedUserDto.getPlayerId().equals(playerId);
    }
}
