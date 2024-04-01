package com.castruche.cast_games_api.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class User extends AbstractEntity{

    @OneToOne(fetch = FetchType.LAZY)
    private Player player;
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;

    private boolean mailVerified;
    private String mailVerificationToken;
    private String resetPasswordToken;

    private boolean blocked;

    private int tentatives;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isMailVerified() {
        return mailVerified;
    }

    public void setMailVerified(boolean mailVerified) {
        this.mailVerified = mailVerified;
    }

    public String getMailVerificationToken() {
        return mailVerificationToken;
    }

    public void setMailVerificationToken(String mailVerificationToken) {
        this.mailVerificationToken = mailVerificationToken;
    }

    public String getResetPasswordToken() {
        return resetPasswordToken;
    }

    public void setResetPasswordToken(String resetPasswordToken) {
        this.resetPasswordToken = resetPasswordToken;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public int getTentatives() {
        return tentatives;
    }

    public void setTentatives(int tentatives) {
        this.tentatives = tentatives;
    }
}
