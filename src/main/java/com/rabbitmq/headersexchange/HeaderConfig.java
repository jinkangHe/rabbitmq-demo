package com.rabbitmq.headersexchange;

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
public class HeaderConfig {

    @Bean("headerExchange")
    public Exchange headerExchange() {
        return ExchangeBuilder.headersExchange("headerExchange").durable(true).build();
    }


    @Bean("queueEInHeaderExchange")
    public Queue queueEInFanoutExchange() {
        return QueueBuilder.durable("queueEInHeaderExchange").build();
    }

    @Bean("queueFInHeaderExchange")
    public Queue queueFInFanoutExchange() {
        return QueueBuilder.durable("queueFInHeaderExchange").build();
    }

    @Bean
    public Binding bindingBuilderForE(Queue queueEInHeaderExchange, HeadersExchange headerExchange) {
        return BindingBuilder.bind(queueEInHeaderExchange).to(headerExchange).whereAll("winter").exist();
    }

    @Bean
    public Binding bindingBuilderForF(Queue queueFInHeaderExchange, HeadersExchange headerExchange) {
        return BindingBuilder.bind(queueFInHeaderExchange).to(headerExchange).whereAny("winter", "snow").exist();
    }
}
