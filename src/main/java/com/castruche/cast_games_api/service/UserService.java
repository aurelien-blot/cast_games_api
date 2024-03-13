package com.castruche.cast_games_api.service;

import com.castruche.cast_games_api.dao.UserRepository;
import com.castruche.cast_games_api.dto.UserDto;
import com.castruche.cast_games_api.dto.standardResponse.BooleanResponseDto;
import com.castruche.cast_games_api.entity.User;
import com.castruche.cast_games_api.formatter.UserFormatter;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService extends GenericService<User, UserDto>{

    private final UserRepository userRepository;
    private UserFormatter userFormatter;

    public UserService(UserRepository userRepository, UserFormatter userFormatter) {
        super(userRepository, userFormatter);
        this.userRepository = userRepository;
        this.userFormatter = userFormatter;
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

}
