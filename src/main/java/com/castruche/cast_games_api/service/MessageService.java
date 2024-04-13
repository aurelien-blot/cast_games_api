package com.castruche.cast_games_api.service;


import com.castruche.cast_games_api.configuration.RabbitMqConfig;
import com.castruche.cast_games_api.dto.message.MessageDto;
import com.castruche.cast_games_api.dto.standardResponse.BooleanResponseDto;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    private static final Logger logger = LogManager.getLogger(MessageService.class);
    private RabbitTemplate rabbitTemplate;
    public MessageService(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
    }

    @Transactional
    public BooleanResponseDto sendMessage(MessageDto messageDto){
        rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE_NAME, RabbitMqConfig.ROUTING_KEY, messageDto);
        return null;
    }


}
