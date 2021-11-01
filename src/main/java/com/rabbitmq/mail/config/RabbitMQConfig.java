package com.rabbitmq.mail.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    //交换机名称
    public static final String MAIL_TOPIC_EXCHANGE = "mail_topic_exchange";
    //队列名称
    public static final String MAIL_QUEUE = "mail_queue";

    //声明交换机
    @Bean("mailTopicExchange")
    public Exchange topicExchange(){

        return ExchangeBuilder.topicExchange(MAIL_TOPIC_EXCHANGE).durable(true).build();
    }

    //声明队列
    @Bean("mailQueue")
    public Queue mailQueue(){
        return QueueBuilder.durable(MAIL_QUEUE).build();
    }

    //绑定队列和交换机
    @Bean
    public Binding itemQueueExchange(@Qualifier("mailQueue") Queue mailQueue,
                                    @Qualifier("mailTopicExchange") Exchange mailTopicExchange){
        return BindingBuilder.bind(mailQueue).to(mailTopicExchange).with("mail.#").noargs();
    }

}
