package com.rabbitmq.mail.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DirectConfig {

    @Bean("directExchange")
    public Exchange fanoutExchange(){
        return ExchangeBuilder.directExchange("directExchange").durable(true).build();
    }

    @Bean("directQueueA")
    public Queue queueA(){
        return QueueBuilder.durable("directQueueA").build();
    }

    @Bean("directQueueB")
    public Queue queueB(){
        return QueueBuilder.durable("directQueueB").build();
    }

    @Bean
    public Binding directExchangeA(@Qualifier("directQueueA")Queue queue, @Qualifier("directExchange")DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("directQueueAKey");
    }

    @Bean
    public Binding directExchangeB(@Qualifier("directQueueB")Queue queue, @Qualifier("directExchange")DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("directQueueBKey");
    }
}
