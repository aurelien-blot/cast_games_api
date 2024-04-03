package com.castruche.cast_games_api.service;

import com.castruche.cast_games_api.configuration.JwtUtil;
import com.castruche.cast_games_api.dao.UserRepository;
import com.castruche.cast_games_api.dto.standardResponse.BooleanResponseDto;
import com.castruche.cast_games_api.dto.user.UserDto;
import com.castruche.cast_games_api.entity.User;
import com.castruche.cast_games_api.formatter.UserFormatter;
import com.castruche.cast_games_api.service.util.MailService;
import com.castruche.cast_games_api.service.util.SettingService;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    private SettingService settingService;
    private MailService mailService;
    private UserRepository userRepository;

    private UserFormatter userFormatter;

    private JwtUtil jwtTokenUtil;
    private static final Logger logger = LogManager.getLogger(SecurityService.class);

    public SecurityService(SettingService settingService,
                           JwtUtil jwtTokenUtil,
                           UserRepository userRepository, MailService mailService,
                           UserFormatter userFormatter) {
        this.settingService = settingService;
        this.userRepository = userRepository;
        this.mailService = mailService;
        this.userFormatter = userFormatter;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Transactional
    public BooleanResponseDto checkPassword(String password, User user) {
        BooleanResponseDto result = new BooleanResponseDto();
        boolean passwordMatch= doPasswordsMatch(password, user.getPassword());
        if(!passwordMatch){
            user.setTentatives(user.getTentatives()+1);
            checkTentativesConnexions(user, result);
        }else{
            user.setTentatives(0);
            result.setStatus(true);
            result.setMessage("Les identifiants de connexion sont corrects.");
        }
        this.userRepository.save(user);
        return result;
    }

    private BooleanResponseDto checkTentativesConnexions(User user, BooleanResponseDto result) {
        Integer tentativesMax = settingService.getTentativesBeforeBlocking();
        if(null==tentativesMax){
            throw new IllegalArgumentException("Le nombre de tentatives avant blocage n'est pas défini.");
        }
        if(user.getTentatives()>=tentativesMax){
            user.setBlocked(true);
            UserDto userDto = userFormatter.entityToDto(user);
            if(user.getTentatives()==tentativesMax){
                user.setResetPasswordToken(jwtTokenUtil.generateToken(user.getUsername()));
                this.mailService.sendMailForPasswordReset(userDto, user.getResetPasswordToken());
            }
            result.setStatus(false);
            result.setMessage("Votre compte a été bloqué suite à un trop grand nombre de tentatives de connexion.\nUn mail vous a été envoyé pour réinitialiser votre mot de passe." +
                    "\nVous pouvez aussi cliquer sur \"Mot de passe oublié\" pour en recevoir un nouveau.");
        }
        else{
            result.setStatus(false);
            result.setMessage("Les identifiants de connexion sont incorrects.");
        }
        return result;
    }

    public String encodePassword(String password){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

    private boolean doPasswordsMatch(String password1, String password2){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(password1, password2);
    }
    public BooleanResponseDto comparePassword(String password1 , String password2, String errorMessage){
        BooleanResponseDto result = new BooleanResponseDto();
        result.setStatus(doPasswordsMatch(password1, password2));
        if(!result.isStatus()){
            if(null==errorMessage){
                result.setMessage("Les mots de passe ne correspondent pas.");
            }
            else{
                result.setMessage(errorMessage);
            }
        }
        else{
            result.setMessage("Les mots de passe correspondent.");
        }

        return result;
    }

}
