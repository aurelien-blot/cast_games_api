package com.castruche.cast_games_api.controller;

import com.castruche.cast_games_api.dto.login.LoginResponseDto;
import com.castruche.cast_games_api.dto.login.LoginUserDto;
import com.castruche.cast_games_api.dto.UserDto;
import com.castruche.cast_games_api.dto.standardResponse.BooleanResponseDto;
import com.castruche.cast_games_api.service.UserService;
import org.springframework.web.bind.annotation.*;

import static com.castruche.cast_games_api.controller.ConstantUrl.LOGIN;

@RestController
@RequestMapping(LOGIN)
public class LoginController {

    private final UserService userService;
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    public LoginResponseDto login(@RequestBody LoginUserDto userDto) {
        return userService.login(userDto);
    }
    @PostMapping("/register")
    public UserDto register(@RequestBody UserDto userDto) {
        return userService.register(userDto);
    }

    @GetMapping("/availability/username/{username}")
    public BooleanResponseDto checkUsernameAvailability(@PathVariable("username") String username) {
        return userService.checkUsernameAvailability(username);
    }

    @GetMapping("/availability/mail/{mail}")
    public BooleanResponseDto checkMailAvailability(@PathVariable("mail") String mail) {
        return userService.checkMailAvailability(mail);
    }
}
