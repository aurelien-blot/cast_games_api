package com.castruche.cast_games_api.dto.util;

public class MailUpdateDto {
    private String newMail;
    private String password;

    public String getNewMail() {
        return newMail;
    }

    public void setNewMail(String newMail) {
        this.newMail = newMail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
