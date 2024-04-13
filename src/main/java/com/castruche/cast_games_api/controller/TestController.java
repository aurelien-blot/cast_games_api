package com.castruche.cast_games_api.controller;

import com.castruche.cast_games_api.dto.message.MessageDto;
import com.castruche.cast_games_api.dto.user.UserDto;
import com.castruche.cast_games_api.service.MessageService;
import com.castruche.cast_games_api.service.util.MailService;
import org.springframework.web.bind.annotation.*;

import static com.castruche.cast_games_api.controller.ConstantUrl.TEST;

@RestController
@RequestMapping(TEST)
public class TestController {

    private final MailService mailService;
    private final MessageService messageService;
    public TestController(MailService mailService, MessageService messageService) {
        this.mailService = mailService;
        this.messageService = messageService;
    }

    @GetMapping("/mail")
    public void testMail() {
        UserDto userDto = new UserDto();
        userDto.setEmail("ch07@hotmail.fr");
        userDto.setFirstName("Jacky");
        mailService.sendMailForMailVerification(userDto, "12345678");
    }

    @GetMapping("/message")
    public void testMessage() {
        MessageDto messageDto = new MessageDto();
        messageDto.setContent("Hello World");
        messageService.sendMessage(messageDto);
    }

}
