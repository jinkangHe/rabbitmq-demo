package com.rabbitmq.ttl;

import com.rabbitmq.dlx.DlxConfig;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class TTLConfig {
    /**
     * 复用DirectConfig定义的直连交换机 和 DlxConfig的死信交换机
     * 创建一个设置过期时间的队列
     * @see com.rabbitmq.direct.DirectConfig
     * @see com.rabbitmq.dlx.DlxConfig
     */
    //创建一个队列绑定到创建一个directExchange
    @Bean("queueTTL")
    public Queue queueTTL() {
        Map params = new HashMap();
        //设置过期时间30S
        params.put("x-message-ttl",30000);
        params.put("x-dead-letter-exchange", "dlxExchange");
        params.put("x-dead-letter-routing-key", "dlx");
        return QueueBuilder.durable("queueTTL").withArguments(params).build();
    }

    /**
     * 绑定队列和交换机 并设置routingKey
     * @param queueTTL
     * @param directExchange
     * @return
     */
    @Bean
    public Binding bindingBuilder(Queue queueTTL, DirectExchange directExchange){
        return BindingBuilder.bind(queueTTL).to(directExchange).with("ttl");
    }




}
