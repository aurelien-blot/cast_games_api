package com.castruche.cast_games_api.service;

import com.castruche.cast_games_api.configuration.JwtUtil;
import com.castruche.cast_games_api.dao.UserRepository;
import com.castruche.cast_games_api.dto.UserDto;
import com.castruche.cast_games_api.dto.login.LoginResponseDto;
import com.castruche.cast_games_api.dto.login.LoginUserDto;
import com.castruche.cast_games_api.dto.util.ResetPasswordDto;
import com.castruche.cast_games_api.dto.util.UserMailDto;
import com.castruche.cast_games_api.dto.standardResponse.BooleanResponseDto;
import com.castruche.cast_games_api.entity.User;
import com.castruche.cast_games_api.formatter.UserFormatter;
import com.castruche.cast_games_api.service.util.MailService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService extends GenericService<User, UserDto>{

    private static final Logger logger = LogManager.getLogger(LoginService.class);
    private final UserRepository userRepository;
    private UserFormatter userFormatter;
    private JwtUtil jwtTokenUtil;

    private MailService mailService;

    public LoginService(UserRepository userRepository, UserFormatter userFormatter, JwtUtil jwtTokenUtil, MailService mailService) {
        super(userRepository, userFormatter);
        this.userRepository = userRepository;
        this.userFormatter = userFormatter;
        this.jwtTokenUtil = jwtTokenUtil;
        this.mailService = mailService;
    }

    public BooleanResponseDto checkUsernameAvailability(String username) {
        Boolean exists = userRepository.existsByUsername(username);
        BooleanResponseDto response = new BooleanResponseDto();
        response.setStatus(!BooleanUtils.isTrue(exists));
        if(response.isStatus()){
            response.setMessage("Nom d'utilisateur disponible.");
        } else {
            response.setMessage("Le nom d'utilisateur " + username + " est déjà utilisé. Veuillez en choisir un autre.");
        }
        return response;
    }

    public BooleanResponseDto checkMailAvailability(String mail) {
        Boolean exists = userRepository.existsByEmail(mail);
        BooleanResponseDto response = new BooleanResponseDto();
        response.setStatus(!BooleanUtils.isTrue(exists));
        if(response.isStatus()){
            response.setMessage("Adresse Email disponible.");
        } else {
            response.setMessage("L'adresse' " + mail + " est déjà utilisée.");
        }
        return response;
    }

    @Transactional
    public UserDto register(UserDto userDto) {
        User user = userFormatter.dtoToEntity(userDto);
        String password = userDto.getPassword();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(password));
        user.setMailVerificationToken(jwtTokenUtil.generateToken(user.getEmail()));
        this.userRepository.save(user);
        UserDto userDtoSaved = selectDtoById(user.getId());
        this.mailService.sendMailForRegistration(userDtoSaved);
        this.mailService.sendMailForMailVerification(userDtoSaved, user.getMailVerificationToken());
        return userDtoSaved;
    }

    public LoginResponseDto login(LoginUserDto userDto) throws EntityNotFoundException {
        LoginResponseDto response = new LoginResponseDto();
        User user = userRepository.findByUsername(userDto.getIdentifier());
        if(user == null){
            user = userRepository.findByEmail(userDto.getIdentifier());
        }
        if(user == null){
            response.setSuccess(false);
            response.setMessage("Utilisateur non trouvé.");
            return response;
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(encoder.matches(userDto.getPassword(), user.getPassword())){
            response.setSuccess(true);
            response.setMessage("Connexion réussie.");
            final String token = jwtTokenUtil.generateToken(user.getUsername());
            response.setToken(token);
            response.setUser(userFormatter.entityToDto(user));
        }
        else{
            response.setSuccess(false);
            response.setMessage("Identifiants incorrects.");
        }
        return response;
    }

    @Transactional
    public BooleanResponseDto verifyMail(String token) {
        User user = userRepository.findByMailVerificationToken(token);
        BooleanResponseDto response = new BooleanResponseDto();
        if(user == null){
            response.setStatus(false);
            response.setMessage("Token invalide.");
        } else {
            user.setMailVerificationToken(null);
            user.setMailVerified(true);
            userRepository.save(user);
            response.setStatus(true);
            response.setMessage("Email vérifié.");
        }
        return response;
    }

    @Transactional
    public BooleanResponseDto sendResetPasswordMail(UserMailDto userMailDto) {
        String mail = userMailDto.getEmail();
        BooleanResponseDto response = new BooleanResponseDto();
        User user = userRepository.findByEmail(mail);
        response.setStatus(true);
        if(user == null){
            //On ne précise pas volontairement si l'email existe ou non pour des raisons de sécurité
            response.setMessage("Email non envoyé.");
        }
        else {
            user.setResetPasswordToken(jwtTokenUtil.generateToken(user.getEmail()));
            this.userRepository.save(user);
            UserDto userDtoSaved = selectDtoById(user.getId());
            this.mailService.sendMailForPasswordReset(userDtoSaved, user.getResetPasswordToken());
            response.setMessage("Email envoyé.");
        }
        return response;
    }

    @Transactional
    public BooleanResponseDto resetPassword(ResetPasswordDto resetPasswordDto) {
        BooleanResponseDto response = new BooleanResponseDto();

        if(resetPasswordDto == null || resetPasswordDto.getToken() ==null || resetPasswordDto.getPassword() ==null){
            response.setStatus(false);
            response.setMessage("Une donnée est manquante.");
            return response;
        }

        User user = userRepository.findByResetPasswordToken(resetPasswordDto.getToken());

        if(user == null){
            response.setStatus(false);
            response.setMessage("Token invalide.");
        }
        else {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            if(encoder.matches(resetPasswordDto.getPassword(), user.getPassword())){
                response.setStatus(false);
                response.setMessage("Le nouveau mot de passe doit être différent de l'ancien.");
            }
            else{
                user.setPassword(encoder.encode(resetPasswordDto.getPassword()));
                user.setResetPasswordToken(null);
                userRepository.save(user);
                response.setStatus(true);
                response.setMessage("Mot de passe réinitialisé.");
            }
        }
        return response;
    }

}
