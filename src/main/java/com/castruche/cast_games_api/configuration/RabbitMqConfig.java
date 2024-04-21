package com.castruche.cast_games_api.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    public static final String MESSAGE_QUEUE_NAME = "messageQueue";
    public static final String MESSAGE_EXCHANGE_NAME = "messageExchange";
    public static final String MESSAGE_ROUTING_KEY = "messageRoutingKey";
    public static final String CONVERSATION_QUEUE_NAME = "conversationQueue";
    public static final String CONVERSATION_EXCHANGE_NAME = "conversationExchange";
    public static final String CONVERSATION_ROUTING_KEY = "conversationRoutingKey";

    @Bean
    Queue messageQueue() {
        return new Queue(MESSAGE_QUEUE_NAME, true);
    }

    @Bean
    Queue conversationQueue() {
        return new Queue(CONVERSATION_QUEUE_NAME, true);
    }

    @Bean
    DirectExchange messageExchange() {
        return new DirectExchange(MESSAGE_EXCHANGE_NAME);
    }

    @Bean
    DirectExchange conversationExchange() {
        return new DirectExchange(CONVERSATION_EXCHANGE_NAME);
    }
    @Bean
    Binding messageBinding(Queue messageQueue, DirectExchange messageExchange) {
        return BindingBuilder.bind(messageQueue).to(messageExchange).with(MESSAGE_ROUTING_KEY);
    }

    @Bean
    Binding conversationBinding(Queue conversationQueue, DirectExchange conversationExchange) {
        return BindingBuilder.bind(conversationQueue).to(conversationExchange).with(CONVERSATION_ROUTING_KEY);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
