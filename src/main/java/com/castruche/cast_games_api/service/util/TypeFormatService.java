package com.castruche.cast_games_api.service.util;

import org.springframework.stereotype.Service;

@Service
public class TypeFormatService {

    public boolean isMail(String mail) {
        return mail.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
    }

    public boolean isAlpha(String test){
        return test.matches("^[a-zA-Z]*$");
    }

    public boolean isAlphaNumeric(String test){
        return test.matches("^[a-zA-Z0-9]*$");
    }
}
