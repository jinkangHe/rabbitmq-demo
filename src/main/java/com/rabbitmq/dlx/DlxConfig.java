package com.rabbitmq.dlx;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Jinkang He
 * @version 1.0
 * @date 2022/3/25 13:45
 * 死信交换机
 */
@Configuration
public class DlxConfig {
    //1.先创建一个普通的的交换机
    @Bean("normalExchange")
    public Exchange normalExchange() {
        //设置exchange的name 发送消息的时候可以指定
        return ExchangeBuilder.directExchange("normalExchange").durable(true).build();
    }

    //2.创建正常的队列，处理正常消息 并且指定处理死信的交换机以及routingKey
    @Bean("normalQueue")
    public Queue normalQueue() {
        return QueueBuilder.durable("normalQueue")
                .withArgument("x-dead-letter-exchange", "dlxExchange") //指定死信发送到哪个交换机（相当于绑定一个死信交换机）
                .withArgument("x-dead-letter-routing-key", "dlx")
                .build();
    }

    //3.绑定正常的交换机和队列
    @Bean
    public Binding bindNormalExchangeAndQueue() {
        return BindingBuilder.bind(normalQueue()).to((DirectExchange)normalExchange()).with("normal");
    }


    //4.创建死信交换机（其实也是正常的交换机创建流程，只不过他的上游是第二步的队列） 这里使用DirectExchange
    @Bean("dlxExchange")
    public Exchange dlxExchange() {
        //设置exchange的name 发送消息的时候可以指定
        return ExchangeBuilder.directExchange("dlxExchange").durable(true).build();
    }

    //5.创建死信，处理死信消息
    @Bean("dlxQueue")
    public Queue dlxQueue() {
        return QueueBuilder.durable("dlxQueue")
                .build();
    }

    //6.绑定死信的交换机和队列
    @Bean
    public Binding bindDlxExchangeAndQueue() {
        return BindingBuilder.bind(dlxQueue()).to((DirectExchange) dlxExchange()).with("dlx");
    }

}
