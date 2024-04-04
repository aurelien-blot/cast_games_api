package com.castruche.cast_games_api.service;

import com.castruche.cast_games_api.entity.User;
import com.castruche.cast_games_api.service.util.SettingService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class JobService {

    private static final Logger logger = LogManager.getLogger(JobService.class);
    private UserService userService;

    public JobService(UserService userService) {
        this.userService = userService;
    }

    @Scheduled(cron = "0 0 10 * * *")
    public void manageAccountVerification() {
        userService.manageAccountVerification();
    }
}
