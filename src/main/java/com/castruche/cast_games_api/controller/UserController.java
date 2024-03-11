package com.castruche.cast_games_api.controller;

import com.castruche.cast_games_api.dto.UserDto;
import com.castruche.cast_games_api.dto.standardResponse.BooleanResponseDto;
import com.castruche.cast_games_api.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.castruche.cast_games_api.controller.ConstantUrl.USER;

@RestController
@RequestMapping(USER)
public class UserController {

    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping
    public List<UserDto> getAll() {
        return userService.getAllDto();
    }

    @GetMapping("/{id}")
    public UserDto getById(@PathVariable("id") Long id) {
        return userService.selectDtoById(id);
    }

    @GetMapping("/check-username/{username}")
    public BooleanResponseDto checkUsernameAvailability(@PathVariable("username") String username) {
        return userService.checkUsernameAvailability(username);
    }
}
