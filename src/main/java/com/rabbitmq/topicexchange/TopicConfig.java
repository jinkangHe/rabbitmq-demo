package com.rabbitmq.topicexchange;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Jinkang He
 * @version 1.0
 * @date 2022/3/24 15:30
 * 两条队列绑定同一个fanoutExchange，正常结果是两条都能收到消息
 */
@Configuration
public class TopicConfig {

    @Bean("topicExchange")
    public Exchange topicExchange() {
        return ExchangeBuilder.topicExchange("topicExchange").durable(true).build();
    }


    @Bean("queueCInTopicExchange")
    public Queue queueAInTopicExchange() {
        return QueueBuilder.durable("queueCInTopicExchange").build();
    }

    @Bean("queueDInTopicExchange")
    public Queue queueBInTopicExchange() {
        return QueueBuilder.durable("queueDInTopicExchange").build();
    }

    //# 符号：匹配一个或多个词。比如"rabbit.#"既可以匹配到"rabbit.a.b"、"rabbit.a"，也可以匹配到"rabbit.a.b.c"。
    @Bean
    public Binding bindingBuilderForC(Queue queueCInTopicExchange, TopicExchange topicExchange) {
        return BindingBuilder.bind(queueCInTopicExchange).to(topicExchange).with("summer.#");
    }

    //* 符号：有且只匹配一个词。比如 user.*可以匹配到"user.name"、"user.age"，但是匹配不了"user.name.firstname"
    @Bean
    public Binding bindingBuilderForD(Queue queueDInTopicExchange, TopicExchange topicExchange) {
        return BindingBuilder.bind(queueDInTopicExchange).to(topicExchange).with("autumn.*");
    }
}
