package com.castruche.cast_games_api.dto.player;

import com.castruche.cast_games_api.dto.contact.ContactDto;

import java.util.List;

public class PlayerSocialDto {
    private Long id;
    private String username;

    private List<ContactDto> friendList;
    private List<ContactDto> blockedList;
    private List<ContactDto> requestList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<ContactDto> getFriendList() {
        return friendList;
    }

    public void setFriendList(List<ContactDto> friendList) {
        this.friendList = friendList;
    }

    public List<ContactDto> getBlockedList() {
        return blockedList;
    }

    public void setBlockedList(List<ContactDto> blockedList) {
        this.blockedList = blockedList;
    }

    public List<ContactDto> getRequestList() {
        return requestList;
    }

    public void setRequestList(List<ContactDto> requestList) {
        this.requestList = requestList;
    }
}
