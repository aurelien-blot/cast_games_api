package com.castruche.cast_games_api.service;

import com.castruche.cast_games_api.dao.UserRepository;
import com.castruche.cast_games_api.dto.user.UserDto;
import com.castruche.cast_games_api.dto.util.ConnectedUserDto;
import com.castruche.cast_games_api.entity.User;
import com.castruche.cast_games_api.formatter.UserFormatter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService extends GenericService<User, UserDto>{

    private static final Logger logger = LogManager.getLogger(UserService.class);

    private UserRepository userRepository;
    private UserFormatter userFormatter;

    public UserService(UserRepository userRepository, UserFormatter userFormatter) {
        super(userRepository, userFormatter);
        this.userRepository = userRepository;
        this.userFormatter = userFormatter;
    }

    public ConnectedUserDto getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        String username=authentication.getName();
        User user = this.userRepository.findByUsername(username);
        return this.userFormatter.entityToConnectedUserDto(user);
    }

    public boolean isCurrentUserByPlayerId(long playerId){
        ConnectedUserDto connectedUserDto = this.getCurrentUser();
        return connectedUserDto != null && connectedUserDto.getPlayerId() != null && connectedUserDto.getPlayerId().equals(playerId);
    }
}
