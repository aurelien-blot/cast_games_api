package com.castruche.cast_games_api.service;

import com.castruche.cast_games_api.configuration.JwtUtil;
import com.castruche.cast_games_api.dao.UserRepository;
import com.castruche.cast_games_api.dto.standardResponse.BooleanResponseDto;
import com.castruche.cast_games_api.dto.user.UserDto;
import com.castruche.cast_games_api.dto.util.ConnectedUserDto;
import com.castruche.cast_games_api.dto.util.MailUpdateDto;
import com.castruche.cast_games_api.dto.util.PasswordDto;
import com.castruche.cast_games_api.entity.User;
import com.castruche.cast_games_api.formatter.UserFormatter;
import com.castruche.cast_games_api.service.util.MailService;
import com.castruche.cast_games_api.service.util.SettingService;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService extends GenericService<User, UserDto>{

    private static final Logger logger = LogManager.getLogger(UserService.class);

    private UserRepository userRepository;
    private UserFormatter userFormatter;

    private JwtUtil jwtTokenUtil;

    private ConnectedUserService connectedUserService;
    private PlayerService playerService;
    private MailService mailService;
    private SecurityService securityService;
    private SettingService settingService;

    public UserService(UserRepository userRepository, UserFormatter userFormatter,
                       JwtUtil jwtTokenUtil,
                    SecurityService securityService,
                       MailService mailService,
                          SettingService settingsService,
                       PlayerService playerService, ConnectedUserService connectedUserService) {
        super(userRepository, userFormatter);
        this.userRepository = userRepository;
        this.userFormatter = userFormatter;
        this.jwtTokenUtil = jwtTokenUtil;
        this.connectedUserService = connectedUserService;
        this.playerService=playerService;
        this.securityService = securityService;
        this.mailService = mailService;
        this.settingService = settingsService;
    }

    public User findByEmail(String mail){
        return this.userRepository.findByEmail(mail);
    }
    @Transactional
    public BooleanResponseDto deleteAccount(PasswordDto passwordDto) {
        BooleanResponseDto booleanResponseDto = new BooleanResponseDto();
        ConnectedUserDto connectedUserDto = this.connectedUserService.getCurrentUser();
        if (connectedUserDto == null) {
            booleanResponseDto.setStatus(false);
            booleanResponseDto.setMessage("L'utilisateur connecté n'est pas reconnu");
            return booleanResponseDto;
        }
        User user = this.userRepository.findByUsername(connectedUserDto.getUsername());
        if (user == null) {
            booleanResponseDto.setStatus(false);
            booleanResponseDto.setMessage("L'utilisateur connecté n'est pas reconnu");
            return booleanResponseDto;
        }
        BooleanResponseDto checkPasswordResponse = securityService.checkPassword(passwordDto.getPassword(), user);
        if (!checkPasswordResponse.isStatus()) {
            booleanResponseDto.setStatus(false);
            booleanResponseDto.setMessage(checkPasswordResponse.getMessage());
            booleanResponseDto.setCode(checkPasswordResponse.getCode());
            return booleanResponseDto;
        }

        booleanResponseDto.setStatus(true);
        booleanResponseDto.setMessage("Le compte a bien été supprimé, vous allez être redirigé vers la page d'accueil.");
        this.deleteUser(user);
        return booleanResponseDto;
    }

    @Transactional
    public BooleanResponseDto updateMail(MailUpdateDto request) {
        BooleanResponseDto booleanResponseDto = new BooleanResponseDto();
        ConnectedUserDto connectedUserDto = this.connectedUserService.getCurrentUser();
        if (connectedUserDto == null) {
            booleanResponseDto.setStatus(false);
            booleanResponseDto.setMessage("L'utilisateur connecté n'est pas reconnu");
            return booleanResponseDto;
        }
        User user = this.userRepository.findByUsername(connectedUserDto.getUsername());
        if (user == null) {
            booleanResponseDto.setStatus(false);
            booleanResponseDto.setMessage("L'utilisateur connecté n'est pas reconnu");
            return booleanResponseDto;
        }
        BooleanResponseDto checkPasswordResponse = securityService.checkPassword(request.getPassword(), user);
        if (!checkPasswordResponse.isStatus()) {
            return checkPasswordResponse;
        }
        if(request.getNewMail()==null){
            booleanResponseDto.setStatus(false);
            booleanResponseDto.setMessage("La nouvelle adresse mail ne peut pas être vide.");
            return booleanResponseDto;
        }
        if(request.getNewMail().equals(user.getEmail())){
            booleanResponseDto.setStatus(false);
            booleanResponseDto.setMessage("L'adresse mail indiquée est identique à celle de votre compte.");
            return booleanResponseDto;
        }
        Boolean mailAlreadyUsed = this.userRepository.existsByEmail(request.getNewMail());
        if(mailAlreadyUsed){
            booleanResponseDto.setStatus(false);
            booleanResponseDto.setMessage("L'adresse mail indiquée est déjà utilisée.");
            return booleanResponseDto;
        }
        user.setEmail(request.getNewMail());
        user.setMailVerified(false);
        user.setMailVerificationToken(jwtTokenUtil.generateToken(user.getEmail()));
        user.setLastVerificationMailDate(LocalDateTime.now());
        this.userRepository.save(user);
        UserDto userDtoSaved = this.userFormatter.entityToDto(user);
        this.mailService.sendMailForMailVerification(userDtoSaved, user.getMailVerificationToken());
        booleanResponseDto.setStatus(true);
        booleanResponseDto.setMessage("L'adresse mail de votre compte a bien été modifiée, vous allez recevoir un email pour confirmer cette nouvelle adresse.");
        return booleanResponseDto;
    }

    private void deleteUser(User user) {
        UserDto userDto = this.userFormatter.entityToDto(user);
        this.playerService.detachPlayerFromUser(user.getPlayer());
        this.userRepository.delete(user);
        this.mailService.sendMailForAccountDeletion(userDto);
    }

    private void sendVerificationReminderMailList(){
        List<User> unverifiedList = this.userRepository.findByMailVerifiedIsFalseAndLastVerificationMailDateIsNotNullAndLastVerificationMailDateBefore(LocalDate.now().atStartOfDay());
        int count=0;
        //On envoie un mail de rappel si le mail de vérification n'a pas été coché depuis 10, 20 et 27 jours après l'inscription
        for(User user : unverifiedList){
            //On exprime en jours le délai entre la date de création du compte et la date de dernier envoi de mail de vérification
            long daysBetween = user.getCreationTime().until(LocalDate.now().atStartOfDay(), java.time.temporal.ChronoUnit.DAYS);
            List<Integer> daysToCheck = settingService.getMailVerificationDelayList();
            for(Integer i : daysToCheck){
                if(daysBetween==i){
                    user.setMailVerificationToken(jwtTokenUtil.generateToken(user.getEmail()));
                    UserDto userDto = this.userFormatter.entityToDto(user);
                    this.mailService.sendMailForVerificationReminder(userDto, user.getMailVerificationToken(), daysBetween);
                    user.setLastVerificationMailDate(LocalDateTime.now());
                    this.userRepository.save(user);
                    count++;
                }
            }
        }
        logger.info("Nombre de mails de rappel de vérification envoyés : "+count);
    }
    private void deleteUnverifiedAccounts(){
        Integer delay = settingService.getDeletingUnverifiedAccountDelay();
        List<User> unverifiedList = this.userRepository.findByMailVerifiedIsFalseAndCreationTimeBefore(LocalDateTime.now().minusDays(delay));
        int count=0;
        for(User user : unverifiedList){
           this.deleteUser(user);
            count++;
        }
        logger.info("Nombre de comptes non vérifiés supprimés : "+count);
    }

    @Transactional
    public void manageAccountVerification() {
        sendVerificationReminderMailList();
        deleteUnverifiedAccounts();
    }
}
