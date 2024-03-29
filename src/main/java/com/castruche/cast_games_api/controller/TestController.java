package com.castruche.cast_games_api.controller;

import com.castruche.cast_games_api.dto.user.UserDto;
import com.castruche.cast_games_api.service.util.MailService;
import org.springframework.web.bind.annotation.*;

import static com.castruche.cast_games_api.controller.ConstantUrl.TEST;

@RestController
@RequestMapping(TEST)
public class TestController {

    private final MailService mailService;
    public TestController(MailService mailService) {
        this.mailService = mailService;
    }

    @GetMapping("/mail")
    public void testMail() {
        UserDto userDto = new UserDto();
        userDto.setEmail("ch07@hotmail.fr");
        userDto.setFirstName("Jacky");
        mailService.sendMailForMailVerification(userDto, "12345678");
    }

}
