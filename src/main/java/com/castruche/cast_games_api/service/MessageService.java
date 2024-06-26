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
public class MessageService {

    private static final Logger logger = LogManager.getLogger(MessageService.class);
    private RabbitTemplate rabbitTemplate;

    private ConnectedUserService connectedUserService;

    public MessageService(RabbitTemplate rabbitTemplate, ConnectedUserService connectedUserService){
        this.connectedUserService = connectedUserService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Transactional
    public BooleanResponseDto sendMessage(MessageDto messageDto){
        Player senderDto = connectedUserService.getCurrentPlayer();
        if(senderDto == null){
            throw new IllegalArgumentException("Sender not found");
        }
        PlayerExtraLightDto sender = new PlayerExtraLightDto();
        sender.setId(senderDto.getId());
        sender.setUsername(senderDto.getUsername());
        messageDto.setSender(sender);

        BooleanResponseDto response = new BooleanResponseDto();
        try{
            rabbitTemplate.convertAndSend(RabbitMqConfig.MESSAGE_EXCHANGE_NAME, RabbitMqConfig.MESSAGE_ROUTING_KEY, messageDto);
            response.setStatus(true);
            response.setMessage("Demande de requête d'envoi de message envoyée");
            return response;
        } catch (Exception e){
            logger.error("Erreur lors de l'envoi de la requête : " + e.getMessage());
            response.setStatus(false);
            response.setMessage("Erreur lors de l'envoi de la requête d'envoi de message");
        }
        return response;
    }




}
