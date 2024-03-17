package com.castruche.cast_games_api.dto.login;

import com.castruche.cast_games_api.dto.AbstractDto;
import com.castruche.cast_games_api.dto.UserDto;

public class LoginResponseDto extends AbstractDto {

    private boolean success;

    private String message;

    private String token;

    private UserDto user;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }
}
