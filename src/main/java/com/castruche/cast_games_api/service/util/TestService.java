package com.castruche.cast_games_api.service.util;

import com.castruche.cast_games_api.dao.ContactRepository;
import com.castruche.cast_games_api.dao.UserRepository;
import com.castruche.cast_games_api.dto.user.UserDto;
import com.castruche.cast_games_api.dto.util.MailObjectDto;
import com.castruche.cast_games_api.entity.Contact;
import com.castruche.cast_games_api.entity.Player;
import com.castruche.cast_games_api.entity.User;
import com.castruche.cast_games_api.service.PlayerService;
import com.castruche.cast_games_api.service.SecurityService;
import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import com.mailjet.client.resource.Emailv31;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TestService {

    private static final Logger logger = LogManager.getLogger(TestService.class);

    private SecurityService securityService;
    private PlayerService playerService;
    private ContactRepository contactRepository;
    private UserRepository userRepository;

    public TestService(SecurityService securityService, PlayerService playerService,
                          ContactRepository contactRepository,
                       UserRepository userRepository) {
        this.securityService = securityService;
        this.playerService = playerService;
        this.contactRepository = contactRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void insertUser(){
        String[] usernameList = {"Samoussa", "Georges", "Abdul", "Orvid", "Jacques", "Pierre", "Marie", "Sophie", "Julie", "Julien"};
        List<Player> createdPlayers = new ArrayList<>();
        for (String username : usernameList) {
            User user = new User();
            user.setUsername(username);
            user.setFirstName(username.substring(0,2));
            user.setLastName(username.substring(2));
            user.setEmail(username + "@hotmail.fr");
            user.setFirstName(username);
            user.setLastName(username);
            user.setPassword(this.securityService.encodePassword("12345678"));
            this.userRepository.save(user);
            Player newPlayer = playerService.initPlayer(user);
            user.setPlayer(newPlayer);
            user.setLastVerificationMailDate(LocalDateTime.now());
            this.userRepository.save(user);
            createdPlayers.add(newPlayer);
        }
        for(Player player : createdPlayers){
            for(Player contact : createdPlayers){
                if(!player.getId().equals(contact.getId())){
                    Contact newContact = new Contact();
                    newContact.setPlayer(player);
                    newContact.setContact(contact);
                    newContact.setActive(true);
                    newContact.setBlocked(false);
                    newContact.setRejected(false);
                    this.contactRepository.save(newContact);
                }
            }

        }
    }
}
