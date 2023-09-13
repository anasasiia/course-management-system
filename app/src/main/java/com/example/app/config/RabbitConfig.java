package com.example.app.config;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitConfig {

    @Bean
    public CachingConnectionFactory connectionFactory() {
        return new CachingConnectionFactory("localhost");
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }

    @Bean
    public Queue studentQueue() {
        return new Queue("studentQueue");
    }

    @Bean
    public Queue teacherQueue() {
        return new Queue("teacherQueue");
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("directExchange");
    }

    @Bean
    public Binding studentBinding() {
        return BindingBuilder.bind(studentQueue()).to(directExchange()).with("studentQueue");
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(teacherQueue()).to(directExchange()).with("teacherQueue");
    }
}
