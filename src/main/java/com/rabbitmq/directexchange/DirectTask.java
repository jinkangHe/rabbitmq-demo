package com.rabbitmq.directexchange;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

/**
 * @author Jinkang He
 * @version 1.0
 * @date 2022/3/23 14:20
 */
@Component
public class DirectTask {
    private static final Logger logger = LoggerFactory.getLogger(DirectTask.class);
    private static Integer index = 0;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Scheduled(cron = "*/5 * * * * ?")
    public void producer() {
        String suffix = UUID.randomUUID().toString();
        //实验组
        logger.info("DirectTask发送消息: {}", index);
        rabbitTemplate.convertAndSend("directExchange", "spring", "spring类型消息" + index);
        //对照组
        rabbitTemplate.convertAndSend("directExchange", "summer", "spring类型消息" + index);
        index++;

    }

    //指定监听队列
    @RabbitListener(queues = {"queueA"})
    public void consumer(Message message, Channel channel) {
        if (Math.random() > 0.45) {
            logger.info("consumer收到消息：{}", new String(message.getBody()));
            try {
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            } catch (IOException e) {

                e.printStackTrace();
            }
        } else {
            //
            try {
                channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
            } catch (IOException e) {
                e.printStackTrace();
            }
            logger.error("{}消息未消费", new String(message.getBody()));
        }
    }

    //指定监听队列
    @RabbitListener(queues = {"queueA"})
    public void consumerA(Message message, Channel channel) {
      
        logger.info("consumerA收到消息：{}", new String(message.getBody()));


    }

}
