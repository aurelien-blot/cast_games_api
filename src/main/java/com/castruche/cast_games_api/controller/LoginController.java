package com.castruche.cast_games_api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.castruche.cast_games_api.controller.ConstantUrl.LOGIN;

@RestController
@RequestMapping(LOGIN)
public class LoginController {
    @GetMapping
    public String login() {
        //TODO: faire le login
        return "login";
    }
}
