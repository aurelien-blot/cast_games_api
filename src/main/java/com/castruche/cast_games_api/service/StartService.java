package com.castruche.cast_games_api.service;

import com.castruche.cast_games_api.service.util.SettingService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
public class StartService implements CommandLineRunner {
    private static final Logger logger = LogManager.getLogger(StartService.class);
    private UserService userService;

    public StartService(UserService userService) {
        this.userService = userService;
    }
    @Override
    public void run(String... args) throws Exception {
        userService.manageAccountVerification();
    }
}
