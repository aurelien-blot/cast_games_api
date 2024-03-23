package com.castruche.cast_games_api.service;

import com.castruche.cast_games_api.configuration.JwtUtil;
import com.castruche.cast_games_api.dao.UserRepository;
import com.castruche.cast_games_api.dto.login.LoginResponseDto;
import com.castruche.cast_games_api.dto.login.LoginUserDto;
import com.castruche.cast_games_api.dto.UserDto;
import com.castruche.cast_games_api.dto.standardResponse.BooleanResponseDto;
import com.castruche.cast_games_api.entity.User;
import com.castruche.cast_games_api.formatter.UserFormatter;
import com.castruche.cast_games_api.service.util.SettingService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

}
