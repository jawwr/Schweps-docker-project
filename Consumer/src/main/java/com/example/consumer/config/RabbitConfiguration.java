package com.example.consumer.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {
    public static final String QUEUE_NAME = "links";
    public static final String QUEUE_EXCHANGE = "links_exchange";
    public static final String QUEUE_KEY = "links_key";

    @Bean
    public CachingConnectionFactory connectionFactory(){
        return new CachingConnectionFactory("rabbitmq");
    }

    @Bean
    public AmqpAdmin admin(){
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public RabbitTemplate rabbitTemplate(){
        var template = new RabbitTemplate(connectionFactory());
        template.setMessageConverter(new Jackson2JsonMessageConverter());
        return template;
    }

    @Bean
    public Queue myQueue(){
        return new Queue(QUEUE_NAME);
    }

    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(QUEUE_EXCHANGE);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(QUEUE_KEY);
    }
}
