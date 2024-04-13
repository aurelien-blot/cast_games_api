package com.castruche.cast_games_api.dto.message;

import com.castruche.cast_games_api.dto.AbstractDto;

import java.time.LocalDateTime;

public class MessageDto extends AbstractDto {
    private String content;

    private Long conversationId;

    private LocalDateTime readAt;

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
}
