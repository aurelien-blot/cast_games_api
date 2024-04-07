package com.castruche.cast_games_api.service;

import com.castruche.cast_games_api.configuration.JwtUtil;
import com.castruche.cast_games_api.dao.ContactRepository;
import com.castruche.cast_games_api.dao.UserRepository;
import com.castruche.cast_games_api.dto.ContactDto;
import com.castruche.cast_games_api.dto.player.PlayerExtraLightDto;
import com.castruche.cast_games_api.dto.standardResponse.BooleanResponseDto;
import com.castruche.cast_games_api.dto.user.UserDto;
import com.castruche.cast_games_api.dto.util.ConnectedUserDto;
import com.castruche.cast_games_api.dto.util.MailUpdateDto;
import com.castruche.cast_games_api.dto.util.PasswordDto;
import com.castruche.cast_games_api.entity.Contact;
import com.castruche.cast_games_api.entity.Player;
import com.castruche.cast_games_api.entity.User;
import com.castruche.cast_games_api.formatter.ContactFormatter;
import com.castruche.cast_games_api.formatter.UserFormatter;
import com.castruche.cast_games_api.service.util.MailService;
import com.castruche.cast_games_api.service.util.SettingService;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ContactService extends GenericService<Contact, ContactDto>{

    private static final Logger logger = LogManager.getLogger(ContactService.class);

    private ContactRepository contactRepository;
    private ContactFormatter contactFormatter;
    private ConnectedUserService connectedUserService;
    private PlayerService playerService;
    private UserFormatter userFormatter;
    private MailService mailService;

    public ContactService(ContactRepository contactRepository,
                          ContactFormatter contactFormatter,
                          ConnectedUserService connectedUserService,
                          MailService mailService,
                          UserFormatter userFormatter,
                          PlayerService playerService
    ) {
        super(contactRepository, contactFormatter);
        this.contactRepository = contactRepository;
        this.contactFormatter = contactFormatter;
        this.connectedUserService = connectedUserService;
        this.playerService = playerService;
        this.userFormatter = userFormatter;
        this.mailService = mailService;
    }

    @Transactional
    public BooleanResponseDto requestContact(Long contactPlayerId){
        if(null==contactPlayerId){
            throw new IllegalArgumentException("PlayerId is null");
        }
        BooleanResponseDto response = new BooleanResponseDto();

        Player connectedPlayer = this.connectedUserService.getCurrentPlayer();
        if(connectedPlayer == null){
            response.setStatus(false);
            response.setMessage("Le joueur n'est pas connecté.");
        }
        Player contactPlayer = this.playerService.selectById(contactPlayerId);
        if(contactPlayer == null){
            response.setStatus(false);
            response.setMessage("Le joueur n'existe pas.");
        }
        else if(connectedPlayer.getId().equals(contactPlayer.getId())){
            response.setStatus(false);
            response.setMessage("Vous ne pouvez pas vous ajouter vous même.");
        }
        else if(connectedPlayer.getContacts().stream().anyMatch(contact -> contact.getContact().getId().equals(contactPlayerId))){
            response.setStatus(false);
            response.setMessage("Ce joueur est déjà dans vos contacts.");
        }
        else if(contactPlayer.isArchived()){
            response.setStatus(false);
            response.setMessage("Ce joueur n'est plus actif.");
        }
        else{
            response.setStatus(true);
        }
        if(!response.isStatus()){
            return response;
        }
        Contact contact = initContact(connectedPlayer, contactPlayer);
        this.contactRepository.save(contact);
        //TODO notification à l'autre joueur
        UserDto contactUserDto = userFormatter.entityToDto(contactPlayer.getUser());
        this.mailService.sendContactRequestMail(contactUserDto, connectedPlayer.getUsername());
        response.setMessage("Une demande de contact a été envoyée à "+contactPlayer.getUsername()+".");
        return response;
    }

    private Contact initContact(Player player, Player contact){
        Contact newContact = new Contact();
        newContact.setPlayer(player);
        newContact.setContact(contact);
        newContact.setActive(false);
        newContact.setBlocked(false);
        return newContact;
    }

    public List<PlayerExtraLightDto> searchNewContact(String username){
        if(username == null){
            throw new IllegalArgumentException("Username is null");
        }
        List<PlayerExtraLightDto> playerList = this.playerService.searchPlayer(username);
        Player connectedPlayer = this.connectedUserService.getCurrentPlayer();
        List<PlayerExtraLightDto> contacts = new ArrayList<>();
        if(connectedPlayer !=null){
            List<Long> contactPlayerIdList = new ArrayList<>();
            for(Contact contact : connectedPlayer.getContacts()){
                if(contact.getContact() != null){
                    contactPlayerIdList.add(contact.getContact().getId());
                }
            }
            for(PlayerExtraLightDto player : playerList){
                if(!contactPlayerIdList.contains(player.getId()) && !Objects.equals(player.getId(), connectedPlayer.getId()))
                {
                    contacts.add(player);
                }
            }
        }
        return contacts;
    }

}
