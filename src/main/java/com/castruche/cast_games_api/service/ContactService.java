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
import com.castruche.cast_games_api.service.util.TypeFormatService;
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
    private TypeFormatService typeFormatService;
    private UserService userService;

    public ContactService(ContactRepository contactRepository,
                          ContactFormatter contactFormatter,
                          ConnectedUserService connectedUserService,
                          MailService mailService,
                          UserService userService,
                          UserFormatter userFormatter,
                          TypeFormatService typeFormatService,
                          PlayerService playerService
    ) {
        super(contactRepository, contactFormatter);
        this.contactRepository = contactRepository;
        this.contactFormatter = contactFormatter;
        this.connectedUserService = connectedUserService;
        this.playerService = playerService;
        this.userService = userService;
        this.userFormatter = userFormatter;
        this.mailService = mailService;
        this.typeFormatService = typeFormatService;
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


    @Transactional
    public BooleanResponseDto requestContactByMail(String mail) {
        if (null == mail || !this.typeFormatService.isMail(mail)) {
            throw new IllegalArgumentException("Mail is null or is not a mail");
        }
        BooleanResponseDto response = new BooleanResponseDto();
        User connectedUser = this.connectedUserService.getCurrentUserEntity();
        User contactUser = this.userService.findByEmail(mail);
        if (connectedUser == null) {
            response.setStatus(false);
            response.setMessage("Le joueur n'est pas connecté.");
        }
        else if(contactUser !=null && contactUser.getPlayer()!= null){
            return requestContact(contactUser.getPlayer().getId());
        }
        else{
            UserDto userDto = this.userFormatter.entityToDto(connectedUser);
            Contact contact = initContactByMail(connectedUser.getPlayer(), mail);
            this.contactRepository.save(contact);
            this.mailService.sendContactInvitationMail(userDto, mail);
            response.setStatus(true);
            response.setMessage("Une demande de contact a été envoyée à "+mail+".");
        }
        return response;
    }


    private Contact initContact(Player player, Player contact){
        Contact newContact = new Contact();
        newContact.setPlayer(player);
        newContact.setContact(contact);
        newContact.setActive(false);
        newContact.setBlocked(false);
        newContact.setRejected(false);
        return newContact;
    }
    private Contact initContactByMail(Player player, String mail){
        Contact newContact = new Contact();
        newContact.setPlayer(player);
        newContact.setContactMail(mail);
        newContact.setActive(false);
        newContact.setBlocked(false);
        newContact.setRejected(false);
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

    @Transactional
    public BooleanResponseDto acceptFriend(Long requestId){
        if(null==requestId){
            throw new IllegalArgumentException("RequestId is null");
        }
        Contact contact = this.selectById(requestId);
        BooleanResponseDto response = manageContactRequestError(contact);
        if(response.isStatus()){
            validateContact(contact);
            String message;
            if(contact.getPlayer()!=null && contact.getPlayer().getUsername()!=null){
                message = "La demande de contact de "+ contact.getPlayer().getUsername()+" a été acceptée.";
            }
            else{
                message="La demande de contact a été acceptée.";
            }
            response.setMessage(message);
        }
        return response;
    }

    @Transactional
    public BooleanResponseDto rejectFriend(Long requestId){
        if(null==requestId){
            throw new IllegalArgumentException("RequestId is null");
        }
        Contact contact = this.selectById(requestId);
        BooleanResponseDto response = manageContactRequestError(contact);
        if(response.isStatus()){
            contact.setRejected(true);
            this.contactRepository.save(contact);
            String message;
            if(contact.getPlayer()!=null && contact.getPlayer().getUsername()!=null){
                message = "La demande de contact de "+ contact.getPlayer().getUsername()+" a été refusée.";
            }
            else{
                message="La demande de contact a été refusée.";
            }
            response.setMessage(message);
        }
        return response;
    }

    private BooleanResponseDto manageContactRequestError(Contact contact){
        BooleanResponseDto response = new BooleanResponseDto();
        Player connectedPlayer = this.connectedUserService.getCurrentPlayer();
        if(connectedPlayer == null){
            response.setStatus(false);
            response.setMessage("Le joueur n'est pas connecté.");
        }
        else if(contact == null){
            response.setStatus(false);
            response.setMessage("La demande de contact n'existe pas.");
        }
        else if(!contact.getContact().getId().equals(connectedPlayer.getId())){
            response.setStatus(false);
            response.setMessage("Vous n'êtes pas concerné par cette demande de contact.");
        }
        else if(contact.isActive()){
            response.setStatus(false);
            response.setMessage("La demande de contact a déjà été acceptée.");
        }
        else{
            response.setStatus(true);
        }
        return response;
    }

    @Transactional
    public BooleanResponseDto blockContact(Long id){
        if(null==id){
            throw new IllegalArgumentException("Id is null");
        }
        Contact contact = this.selectById(id);
        BooleanResponseDto response = new BooleanResponseDto();
        if(contact == null){
            response.setStatus(false);
            response.setMessage("La demande de contact n'existe pas.");
        }
        else{
            response.setStatus(true);
            contact.setBlocked(true);
            this.contactRepository.save(contact);
            String message;
            if(contact.getContact()!=null && contact.getContact().getUsername()!=null){
                message = contact.getContact().getUsername()+" a été bloqué.";
            }
            else{
                message="Le joueur ou la joueuse a été bloqué.";
            }
            response.setMessage(message);
        }
        return response;
    }

    @Transactional
    public BooleanResponseDto unblockContact(Long id){
        if(null==id){
            throw new IllegalArgumentException("Id is null");
        }
        Contact contact = this.selectById(id);
        BooleanResponseDto response = new BooleanResponseDto();
        if(contact == null){
            response.setStatus(false);
            response.setMessage("La demande de contact n'existe pas.");
        }
        else{
            response.setStatus(true);
            contact.setBlocked(false);
            this.contactRepository.save(contact);
            String message;
            if(contact.getContact()!=null && contact.getContact().getUsername()!=null){
                message = contact.getContact().getUsername()+" a été débloqué.";
            }
            else{
                message="Le joueur ou la joueuse a été débloqué.";
            }
            response.setMessage(message);
        }
        return response;
    }

    private void validateContact(Contact contact){
        contact.setActive(true);
        contact.setRejected(false);
        contact.setBlocked(false);
        this.contactRepository.save(contact);
        createSymetryContact(contact);
    }

    private void createSymetryContact(Contact contact){
        Contact symetryContact = new Contact();
        symetryContact.setPlayer(contact.getContact());
        symetryContact.setContact(contact.getPlayer());
        symetryContact.setActive(true);
        symetryContact.setBlocked(false);
        symetryContact.setRejected(false);
        this.contactRepository.save(symetryContact);
    }

    @Transactional
    public BooleanResponseDto deleteContact(Long id){
        if(null==id){
            throw new IllegalArgumentException("Id is null");
        }
        Contact contact = this.selectById(id);
        BooleanResponseDto response = new BooleanResponseDto();
        if(contact == null){
            response.setStatus(false);
            response.setMessage("La demande de contact n'existe pas.");
        }
        else{
            response.setStatus(true);
            this.deleteSymetryContact(contact);
            this.contactRepository.delete(contact);
            String message;
            if(contact.getContact()!=null && contact.getContact().getUsername()!=null){
                message = contact.getContact().getUsername()+" ne fait plus partie de vos amis.";
            }
            else{
                message="Le joueur ou la joueuse ne fait plus partie de vos amis.";
            }
            response.setMessage(message);
        }
        return response;
    }

    private void deleteSymetryContact(Contact contact){
        Contact symetryContact = this.contactRepository.findByPlayerAndContact(contact.getContact(), contact.getPlayer());
        if(symetryContact != null){
            this.contactRepository.delete(symetryContact);
        }
    }

    @Transactional
    public void transformMailContactToUserContact(User user){
        List<Contact> contactList = this.contactRepository.findByContactMail(user.getEmail());
        for(Contact contact : contactList){
            contact.setContactMail(null);
            contact.setContact(user.getPlayer());
            this.contactRepository.save(contact);
        }
    }

}
