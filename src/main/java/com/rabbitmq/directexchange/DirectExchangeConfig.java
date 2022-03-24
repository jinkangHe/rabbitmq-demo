package com.rabbitmq.directexchange;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Jinkang He
 * @version 1.0
 * @date 2022/3/23 13:54
 * 声明一个队列 一个交换机 然后将两者绑定到一起 durable 表示持久化 重启之后exchange不消失
 */

@Configuration
public class DirectExchangeConfig {
    //创建一个directExchange
    @Bean("directExchange")
    public Exchange directExchange(){
        //设置exchange的name 发送消息的时候可以指定
        return ExchangeBuilder.directExchange("directExchange").durable(true).build();
    }

    //创建一个队列绑定到创建一个directExchange
    @Bean("queueA")
    public Queue queueA() {
        return QueueBuilder.durable("queueA").build();
    }

    @Bean
    public Binding bindingBuilder(Queue queueA,DirectExchange directExchange){
        return BindingBuilder.bind(queueA).to(directExchange).with("spring");
    }
}
