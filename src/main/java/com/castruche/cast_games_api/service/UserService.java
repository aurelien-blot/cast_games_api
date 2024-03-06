package com.castruche.cast_games_api.service;

import com.castruche.cast_games_api.dao.UserRepository;
import com.castruche.cast_games_api.dto.UserDto;
import com.castruche.cast_games_api.entity.User;
import com.castruche.cast_games_api.formatter.UserFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService extends GenericService<User, UserDto>{

    private UserRepository userRepository;
    private UserFormatter userFormatter;

    @Autowired
    public UserService(UserRepository userRepository, UserFormatter userFormatter) {
        super(userRepository, userFormatter);
        this.userRepository = userRepository;
        this.userFormatter = userFormatter;
    }

}
