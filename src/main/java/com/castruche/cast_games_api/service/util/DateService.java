package com.castruche.cast_games_api.service.util;

import com.castruche.cast_games_api.dto.user.UserDto;
import com.castruche.cast_games_api.dto.util.MailObjectDto;
import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import com.mailjet.client.resource.Emailv31;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class DateService {

    private static final Logger logger = LogManager.getLogger(DateService.class);

    public LocalDateTime getStartOfDay(LocalDateTime date) {
        if(date == null) {
            return null;
        }
        return date.withHour(0).withMinute(0).withSecond(0).withNano(0);
    }
}
