package com.castruche.cast_games_api.controller;

import com.castruche.cast_games_api.dto.ContactDto;
import com.castruche.cast_games_api.dto.player.*;
import com.castruche.cast_games_api.dto.standardResponse.BooleanResponseDto;
import com.castruche.cast_games_api.service.ContactService;
import com.castruche.cast_games_api.service.PlayerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.castruche.cast_games_api.controller.ConstantUrl.CONTACT;
import static com.castruche.cast_games_api.controller.ConstantUrl.PLAYER;

@RestController
@RequestMapping(CONTACT)
public class ContactController {

    private final ContactService contactService;
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping("/request/{playerId}")
    public BooleanResponseDto requestContact(@PathVariable("playerId") Long playerId) {
        return contactService.requestContact(playerId);
    }

    @PostMapping("/search/new/{username}")
    public List<PlayerExtraLightDto> searchPlayer(@PathVariable("username") String username) {
        return contactService.searchNewContact(username);
    }

    @PostMapping("/accept/{requestId}")
    public BooleanResponseDto acceptFriend(@PathVariable("requestId") Long requestId) {
        return contactService.acceptFriend(requestId);
    }

    @PostMapping("/reject/{requestId}")
    public BooleanResponseDto rejectFriend(@PathVariable("requestId") Long requestId) {
        return contactService.rejectFriend(requestId);
    }
}
