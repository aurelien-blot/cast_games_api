package com.castruche.cast_games_api.controller;

import com.castruche.cast_games_api.dto.game.GameDto;
import com.castruche.cast_games_api.dto.message.MessageDto;
import com.castruche.cast_games_api.dto.user.UserDto;
import com.castruche.cast_games_api.service.MessageService;
import com.castruche.cast_games_api.service.game.GameService;
import com.castruche.cast_games_api.service.util.MailService;
import com.castruche.cast_games_api.service.util.TestService;
import org.springframework.web.bind.annotation.*;

import static com.castruche.cast_games_api.controller.ConstantUrl.TEST;

@RestController
@RequestMapping(TEST)
public class TestController {

    private final MailService mailService;
    private final TestService testService;
    private final MessageService messageService;
    private final GameService gameService;

    public TestController(MailService mailService,
                            TestService testService,
                          GameService gameService,
                          MessageService messageService) {
        this.mailService = mailService;
        this.testService = testService;
        this.messageService = messageService;
        this.gameService = gameService;
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

    @GetMapping("/insert-user")
    public void insertUser() {
        testService.insertUser();
    }

    @GetMapping("/yams")
    public GameDto getYams() {
        return gameService.getYams();
    }

    @GetMapping("/battle")
    public GameDto getBattle() {
        return gameService.getBattle();
    }

}
