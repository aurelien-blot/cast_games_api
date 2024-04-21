package com.castruche.cast_games_api.service;


import com.castruche.cast_games_api.configuration.RabbitMqConfig;
import com.castruche.cast_games_api.dto.message.MessageDto;
import com.castruche.cast_games_api.dto.player.PlayerExtraLightDto;
import com.castruche.cast_games_api.dto.standardResponse.BooleanResponseDto;
import com.castruche.cast_games_api.dto.util.ConnectedUserDto;
import com.castruche.cast_games_api.entity.Player;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class ConversationService {

    private static final Logger logger = LogManager.getLogger(ConversationService.class);
    private RabbitTemplate rabbitTemplate;

    private ConnectedUserService connectedUserService;

    public ConversationService(RabbitTemplate rabbitTemplate, ConnectedUserService connectedUserService){
        this.connectedUserService = connectedUserService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Transactional
    public BooleanResponseDto initConversationList(){
        ConnectedUserDto connectedUserDto = connectedUserService.getCurrentUser();
        if(connectedUserDto == null){
            throw new IllegalArgumentException("Connected user not found");
        }
        BooleanResponseDto response = new BooleanResponseDto();
        try{
            rabbitTemplate.convertAndSend(RabbitMqConfig.CONVERSATION_EXCHANGE_NAME, RabbitMqConfig.CONVERSATION_ROUTING_KEY, connectedUserDto.getPlayerId());
            response.setStatus(true);
            response.setMessage("Liste des conversations demandée");
            return response;
        } catch (Exception e){
            logger.error("Erreur lors de l'envoi de la requête: " + e.getMessage());
            response.setStatus(false);
            response.setMessage("Erreur lors de l'envoi de la requête de liste des conversations");
        }
        return response;
    }




}
