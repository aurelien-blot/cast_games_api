package com.castruche.cast_games_api.dto.login;

import com.castruche.cast_games_api.dto.AbstractDto;

public class LoginUserDto extends AbstractDto {
    private String identifier;
    private String password;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
