package com.castruche.cast_games_api.dto.message;

import com.castruche.cast_games_api.dto.AbstractDto;
import com.castruche.cast_games_api.dto.player.PlayerExtraLightDto;

import java.time.LocalDateTime;
import java.util.List;

public class MessageDto extends AbstractDto {
    private Long conversationId;
    private String content;
    private List<PlayerExtraLightDto> members;
    private LocalDateTime readAt;
    private PlayerExtraLightDto sender;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getConversationId() {
        return conversationId;
    }

    public void setConversationId(Long conversationId) {
        this.conversationId = conversationId;
    }

    public LocalDateTime getReadAt() {
        return readAt;
    }

    public void setReadAt(LocalDateTime readAt) {
        this.readAt = readAt;
    }

    public List<PlayerExtraLightDto> getMembers() {
        return members;
    }

    public void setMembers(List<PlayerExtraLightDto> members) {
        this.members = members;
    }

    public PlayerExtraLightDto getSender() {
        return sender;
    }

    public void setSender(PlayerExtraLightDto sender) {
        this.sender = sender;
    }
}
