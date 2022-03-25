package com.rabbitmq.headers;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jinkang He
 * @version 1.0
 * @date 2022/3/24 15:30
 * header类型的exchange 分为 存在相同键 和 存在相同键和值两种情况
 * 又分为whereAll（全部存在） 和 whereAny（存在一个即可） 两种情况
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

    @Bean("queueGInHeaderExchange")
    public Queue queueGInHeaderExchange() {
        return QueueBuilder.durable("queueGInHeaderExchange").build();
    }

    @Bean("queueHInHeaderExchange")
    public Queue queueHInHeaderExchange() {
        return QueueBuilder.durable("queueHInHeaderExchange").build();
    }

    @Bean
    public Binding bindingBuilderForE(Queue queueEInHeaderExchange, HeadersExchange headerExchange) {
        return BindingBuilder.bind(queueEInHeaderExchange).to(headerExchange).whereAll("winter","snow").exist();
    }

    @Bean
    public Binding bindingBuilderForF(Queue queueFInHeaderExchange, HeadersExchange headerExchange) {
        return BindingBuilder.bind(queueFInHeaderExchange).to(headerExchange).whereAny("winter", "snow").exist();
    }

    @Bean
    public Binding bindingBuilderForG(Queue queueGInHeaderExchange, HeadersExchange headerExchange) {
        Map<String,Object> headers = new HashMap<>();
        headers.put("name", "hjk");
        headers.put("age", 10);
        return BindingBuilder.bind(queueGInHeaderExchange).to(headerExchange).whereAny(headers).match();
    }

    @Bean
    public Binding bindingBuilderForH(Queue queueHInHeaderExchange, HeadersExchange headerExchange) {
        Map<String,Object> headers = new HashMap<>();
        headers.put("name", "hjk");
        headers.put("age", 10);
        return BindingBuilder.bind(queueHInHeaderExchange).to(headerExchange).whereAll(headers).match();
    }
}
