package com.example.posts_service.infra.communication;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMQConfigurations {
    
    @Value("${spring.rabbitmq.exchange.app}")
    private String EXCHANGE;

    @Value("${spring.rabbitmq.queue.posts}")
    private String POSTS_QUEUE;

    @Value("${spring.rabbitmq.routing.key.user}")
    private String ROUTING_KEY;

    @Bean
    public Queue postsQueue() {
        return new Queue(POSTS_QUEUE, true);
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    public Binding bindingPostsQueue(Queue postsQueue, DirectExchange exchange) {
        return BindingBuilder.bind(postsQueue).to(exchange).with(ROUTING_KEY);
    }

}
