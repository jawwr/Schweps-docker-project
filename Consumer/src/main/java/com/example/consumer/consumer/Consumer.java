package com.example.consumer.consumer;

import com.example.consumer.models.Link;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@EnableRabbit
public class Consumer {

    @RabbitListener(queues = "links")
    public void listener(String link) {
        System.out.println(link);
    }
}
