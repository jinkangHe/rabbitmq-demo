package com.rabbitmq.fanoutexchange;

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
public class FanoutConfig {

    @Bean("fanoutExchange")
    public Exchange fanoutExchange() {
        return ExchangeBuilder.fanoutExchange("fanoutExchange").durable(true).build();
    }


    @Bean("queueAInFanoutExchange")
    public Queue queueAInFanoutExchange() {
        return QueueBuilder.durable("queueAInFanoutExchange").build();
    }

    @Bean("queueBInFanoutExchange")
    public Queue queueBInFanoutExchange() {
        return QueueBuilder.durable("queueBInFanoutExchange").build();
    }

    @Bean
    public Binding bindingBuilderForA(Queue queueAInFanoutExchange, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(queueAInFanoutExchange).to(fanoutExchange);
    }

    @Bean
    public Binding bindingBuilderForB(Queue queueBInFanoutExchange, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(queueBInFanoutExchange).to(fanoutExchange);
    }
}
