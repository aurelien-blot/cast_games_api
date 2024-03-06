package com.castruche.cast_games_api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.castruche.cast_games_api.controller.ConstantUrl.LOGIN;

@Controller
@RequestMapping(LOGIN)
public class LoginController {
    @GetMapping
    public String login() {
        return "login";
    }
}
