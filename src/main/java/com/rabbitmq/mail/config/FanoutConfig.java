package com.rabbitmq.mail.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FanoutConfig {


    //Exchange topicExchange = ExchangeBuilder.topicExchange("topicExchange").durable(true).build();

    @Bean("fanoutExchange")
    public Exchange fanoutExchange(){
        return ExchangeBuilder.fanoutExchange("fanoutExchange").durable(true).build();
    }

    @Bean("fanoutQueueA")
    public Queue queueA(){
        return QueueBuilder.durable("fanoutQueueA").build();
    }

    @Bean("fanoutQueueB")
    public Queue queueB(){
        return QueueBuilder.durable("fanoutQueueB").build();
    }


    @Bean
    public Binding faultExchangeA(@Qualifier("fanoutQueueA")Queue queue, @Qualifier("fanoutExchange")FanoutExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange);
    }

    @Bean
    public Binding faultExchangeB(@Qualifier("fanoutQueueB")Queue queue, @Qualifier("fanoutExchange")FanoutExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange);
    }



   /* @Bean
    public Binding topicExchangePound() {
        return BindingBuilder
                .bind(QueueBuilder.durable("topicQueue#").build())
                .to(topicExchange)
                .with("mail.#")
                .noargs();
    }

    @Bean
    public Binding topicExchangeAsterisk() {
        return BindingBuilder
                .bind(QueueBuilder.durable("topicQueue*").build())
                .to(topicExchange)
                .with("mail.*")
                .noargs();
    }

    @Bean
    public Binding directExchangePound() {
        return BindingBuilder
                .bind(QueueBuilder.durable("directQueue").build())
                .to(ExchangeBuilder.directExchange("directExchange").durable(true).build())
                .with("direct")
                .noargs();
    }*/


}
