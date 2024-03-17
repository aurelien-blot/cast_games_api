package com.castruche.cast_games_api.controller;

import com.castruche.cast_games_api.dto.login.LoginResponseDto;
import com.castruche.cast_games_api.dto.login.LoginUserDto;
import com.castruche.cast_games_api.dto.UserDto;
import com.castruche.cast_games_api.dto.standardResponse.BooleanResponseDto;
import com.castruche.cast_games_api.service.LoginService;
import com.castruche.cast_games_api.service.LoginService;
import org.springframework.web.bind.annotation.*;

import static com.castruche.cast_games_api.controller.ConstantUrl.LOGIN;

@RestController
@RequestMapping(LOGIN)
public class LoginController {

    private final LoginService loginService;
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping()
    public LoginResponseDto login(@RequestBody LoginUserDto userDto) {
        return loginService.login(userDto);
    }
    @PostMapping("/register")
    public UserDto register(@RequestBody UserDto userDto) {
        return loginService.register(userDto);
    }

    @GetMapping("/availability/username/{username}")
    public BooleanResponseDto checkUsernameAvailability(@PathVariable("username") String username) {
        return loginService.checkUsernameAvailability(username);
    }

    @GetMapping("/availability/mail/{mail}")
    public BooleanResponseDto checkMailAvailability(@PathVariable("mail") String mail) {
        return loginService.checkMailAvailability(mail);
    }
}
