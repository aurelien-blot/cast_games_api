package com.castruche.cast_games_api.controller;

import com.castruche.cast_games_api.dto.login.LoginResponseDto;
import com.castruche.cast_games_api.dto.login.LoginUserDto;
import com.castruche.cast_games_api.dto.user.UserDto;
import com.castruche.cast_games_api.dto.util.ResetPasswordDto;
import com.castruche.cast_games_api.dto.util.UserMailDto;
import com.castruche.cast_games_api.dto.standardResponse.BooleanResponseDto;
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

    @GetMapping("/verify/mail/{token}")
    public BooleanResponseDto verifyMail(@PathVariable("token") String token) {
        return loginService.verifyMail(token);
    }

    @PostMapping("/reset-password/request")
    public BooleanResponseDto sendResetPasswordMail(@RequestBody UserMailDto userMailDto) {
        return loginService.sendResetPasswordMail(userMailDto);
    }

    @PostMapping("/reset-password")
    public BooleanResponseDto resetPassword(@RequestBody ResetPasswordDto resetPasswordDto) {
        return loginService.resetPassword(resetPasswordDto);
    }

}
