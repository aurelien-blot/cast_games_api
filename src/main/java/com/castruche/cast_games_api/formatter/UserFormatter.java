package com.castruche.cast_games_api.formatter;

import com.castruche.cast_games_api.dto.UserDto;
import com.castruche.cast_games_api.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserFormatter implements IFormatter<User, UserDto>{

    @Override
    public UserDto entityToDto(User entity) {
        UserDto userDto = new UserDto();
        userDto.setId(entity.getId());
        userDto.setUsername(entity.getUsername());
        userDto.setEmail(entity.getEmail());
        userDto.setFirstName(entity.getFirstName());
        userDto.setLastName(entity.getLastName());
        return userDto;
    }

    @Override
    public User dtoToEntity(UserDto dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        return user;
    }
}
