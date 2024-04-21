package com.castruche.cast_games_api.controller;

import com.castruche.cast_games_api.dto.message.MessageDto;
import com.castruche.cast_games_api.dto.standardResponse.BooleanResponseDto;
import com.castruche.cast_games_api.service.ConversationService;
import com.castruche.cast_games_api.service.MessageService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.castruche.cast_games_api.controller.ConstantUrl.CONVERSATION;
import static com.castruche.cast_games_api.controller.ConstantUrl.MESSAGE;

@RestController
@RequestMapping(CONVERSATION)
public class ConversationController {

    private final ConversationService conversationService;
    public ConversationController(ConversationService conversationService) {
        this.conversationService = conversationService;
    }

    @PostMapping()
    public BooleanResponseDto initConversationList() {
        return conversationService.initConversationList();
    }

}
