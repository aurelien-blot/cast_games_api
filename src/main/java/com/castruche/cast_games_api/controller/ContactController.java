package com.castruche.cast_games_api.controller;

import com.castruche.cast_games_api.dto.player.*;
import com.castruche.cast_games_api.dto.standardResponse.BooleanResponseDto;
import com.castruche.cast_games_api.service.ContactService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.castruche.cast_games_api.controller.ConstantUrl.CONTACT;

@RestController
@RequestMapping(CONTACT)
public class ContactController {

    private final ContactService contactService;
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping("/active")
    public List<PlayerExtraLightDto> getActiveContacts() {
        return contactService.getActiveContacts();
    }


    @PostMapping("/request/{playerId}")
    public BooleanResponseDto requestContact(@PathVariable("playerId") Long playerId) {
        return contactService.requestContact(playerId);
    }

    @PostMapping("/request/mail/{playerMail}")
    public BooleanResponseDto requestContactByMail(@PathVariable("playerMail") String playerMail) {
        return contactService.requestContactByMail(playerMail);
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

    @PostMapping("/block/{id}")
    public BooleanResponseDto blockContact(@PathVariable("id") Long id) {
        return contactService.blockContact(id);
    }

    @PostMapping("/unblock/{id}")
    public BooleanResponseDto unblockContact(@PathVariable("id") Long id) {
        return contactService.unblockContact(id);
    }

    @DeleteMapping("/{id}")
    public BooleanResponseDto deleteContact(@PathVariable("id") Long id) {
        return contactService.deleteContact(id);
    }
}
