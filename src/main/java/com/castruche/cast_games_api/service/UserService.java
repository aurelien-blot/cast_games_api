package com.castruche.cast_games_api.service;

import com.castruche.cast_games_api.dao.UserRepository;
import com.castruche.cast_games_api.dto.standardResponse.BooleanResponseDto;
import com.castruche.cast_games_api.dto.user.UserDto;
import com.castruche.cast_games_api.dto.util.ConnectedUserDto;
import com.castruche.cast_games_api.dto.util.PasswordDto;
import com.castruche.cast_games_api.entity.Player;
import com.castruche.cast_games_api.entity.User;
import com.castruche.cast_games_api.formatter.UserFormatter;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService extends GenericService<User, UserDto>{

    private static final Logger logger = LogManager.getLogger(UserService.class);

    private UserRepository userRepository;
    private UserFormatter userFormatter;

    private ConnectedUserService connectedUserService;
    private PlayerService playerService;

    public UserService(UserRepository userRepository, UserFormatter userFormatter,
                       PlayerService playerService, ConnectedUserService connectedUserService) {
        super(userRepository, userFormatter);
        this.userRepository = userRepository;
        this.userFormatter = userFormatter;
        this.connectedUserService = connectedUserService;
        this.playerService=playerService;
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
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if (!bCryptPasswordEncoder.matches(passwordDto.getPassword(), user.getPassword())) {
            booleanResponseDto.setStatus(false);
            booleanResponseDto.setMessage("Le mot de passe ne correspond pas.");
            return booleanResponseDto;
        }
        this.deleteUser(user);
        booleanResponseDto.setStatus(true);
        booleanResponseDto.setMessage("Le compte a bien été supprimé, vous allez être redirigé vers la page d'accueil.");
        return booleanResponseDto;
    }

    private void deleteUser(User user) {
        this.playerService.detachPlayerFromUser(user.getPlayer());
        this.userRepository.delete(user);
    }
}
