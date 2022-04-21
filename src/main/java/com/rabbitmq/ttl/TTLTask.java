package com.rabbitmq.ttl;


import com.rabbitmq.direct.DirectTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Component
public class TTLTask {
    private static final Logger logger = LoggerFactory.getLogger(TTLTask.class);


    @Autowired
    RabbitTemplate rabbitTemplate;

    @Scheduled(cron = "*/20 * * * * ?")
    public void producer() {
        LocalDateTime now1 = LocalDateTime.now();
       /* logger.info("TTLTask发送消息: {}", now1);
        rabbitTemplate.convertAndSend("directExchange", "ttl", "ttl类型消息" + now1);*/

        //给单条消息设置过期时间
        LocalDateTime now2 = LocalDateTime.now();
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setExpiration("10000");
        logger.info("TTLTask发送设置了过期时间的消息: {}", now2);
        Message message = new Message(("ttl类型消息设置过期时间10s"+now2).getBytes(StandardCharsets.UTF_8), messageProperties);
        logger.info("TTLTask发送消息: {}", now2);

        rabbitTemplate.send("directExchange", "ttl", message);

    }
}
