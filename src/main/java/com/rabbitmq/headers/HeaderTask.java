package com.rabbitmq.headers;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author Jinkang He
 * @version 1.0
 * @date 2022/3/24 15:36
 */
@Component
public class HeaderTask {
    private static final Logger logger = LoggerFactory.getLogger(HeaderTask.class);
    private static Integer index = 0;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Scheduled(cron = "*/20 * * * * ?")
    public void HeaderTaskProducer() {
        //实验组
        logger.info("HeaderTaskProducer发送消息: {}", index);
        //routingKey不管写什么都可以
        Message messageA = MessageBuilder.withBody(("header类型消息A" + index).getBytes())
                .setHeader("winter", "冬天")
                .build();

        Message messageB =
                MessageBuilder.withBody(("header类型消息B" + index).getBytes())
                        .setHeader("winter", "冬天")
                        .setHeader("snow", "下雪")
                        .build();

        Message messageC = MessageBuilder.withBody(("header类型消息C" + index).getBytes())
                .setHeader("name", "hjk")
                .build();

        Message messageD = MessageBuilder.withBody(("header类型消息D" + index).getBytes())
                .setHeader("name", "hjk")
                .setHeader("age", 10)
                .build();

        rabbitTemplate.send("headerExchange", null, messageA);
        rabbitTemplate.send("headerExchange", null, messageB);
        rabbitTemplate.send("headerExchange", null, messageC);
        rabbitTemplate.send("headerExchange", null, messageD);

        index++;
    }

    //指定监听队列
    // header存在 winter键 和 snow 键才能收到
    @RabbitListener(queues = {"queueEInHeaderExchange"})
    public void consumerForQueueAInHeaderExchange(Message message, Channel channel) {
        logger.info("consumerForQueueEInHeaderExchange收到消息：{}", new String(message.getBody()));
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            try {
                channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    //指定监听队列
    // header存在 winter键 || snow键 即可收到
    @RabbitListener(queues = {"queueFInHeaderExchange"})
    public void consumerForQueueBInHeaderExchange(Message message, Channel channel) {
        logger.info("consumerForQueueFInHeaderExchange收到消息：{}", new String(message.getBody()));
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            try {
                channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    //指定监听队列
    //header 中存在 name->hjk 键值对 || age->10 键值对 即可收到
    @RabbitListener(queues = {"queueGInHeaderExchange"})
    public void consumerForQueueGInHeaderExchange(Message message, Channel channel) {
        logger.info("consumerForQueueGInHeaderExchange收到消息：{}", new String(message.getBody()));
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            try {
                channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    //指定监听队列
    //header 中存在 name->hjk 键值对 && age->10 键值对才能收到
    @RabbitListener(queues = {"queueHInHeaderExchange"})
    public void consumerForQueueHInHeaderExchange(Message message, Channel channel) {
        logger.info("consumerForQueueHInHeaderExchange收到消息：{}", new String(message.getBody()));
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            try {
                channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
