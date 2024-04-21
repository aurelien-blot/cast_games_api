package com.castruche.cast_games_api.controller;

import com.castruche.cast_games_api.dto.message.MessageDto;
import com.castruche.cast_games_api.dto.standardResponse.BooleanResponseDto;
import com.castruche.cast_games_api.service.MessageService;
import org.springframework.web.bind.annotation.*;

import static com.castruche.cast_games_api.controller.ConstantUrl.MESSAGE;

@RestController
@RequestMapping(MESSAGE)
public class MessageController {

    private final MessageService messageService;
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping()
    public BooleanResponseDto sendMessage(@RequestBody MessageDto messageDto) {
        return messageService.sendMessage(messageDto);
    }

}
